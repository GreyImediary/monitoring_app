package ru.therapyapp.feature_asdas_impl.mvi

import ru.therapyapp.data_asdas.model.AsdasIndexRequestBody
import ru.therapyapp.data_asdas.model.SrbSoeType
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import java.math.RoundingMode
import java.util.Date
import kotlin.math.ln
import kotlin.math.sqrt

class AsdasViewModel(
    patientId: Int,
    private val asdasRepository: ru.therapyapp.data_asdas.AsdasRepository,
) : MviViewModel<AsdasEvent, AsdasState, AsdasSideEffect>(initialState = AsdasState(patientId = patientId)) {
    override fun dispatch(event: AsdasEvent) {
        when (event) {
            is AsdasEvent.OnChangeSrbSoeType -> onChangeSrbSoeType(event.currentType)
            is AsdasEvent.OnSendAnswer -> onSendAnswer(event.questionNumber, event.value)
            is AsdasEvent.OnSendSrbSoeValue -> onSendSrbSoeValue(event.value)
            AsdasEvent.OnSendIndex -> sendIndex()
        }
    }

    private fun onChangeSrbSoeType(currentType: SrbSoeType) {
        intent {
            reduce { state.copy(srbSoeType = currentType) }
        }
    }

    private fun onSendSrbSoeValue(value: Double) {
        intent {
            reduce {
                if (state.srbSoeType == SrbSoeType.SRB) {
                    if (value < 2) {
                        state.copy(srbSoeValue = 2.0)
                    } else {
                        state.copy(
                            srbSoeValue = value
                                .toBigDecimal()
                                .setScale(2, RoundingMode.HALF_EVEN)
                                .toDouble()
                        )
                    }
                } else {
                    state.copy(srbSoeValue = value
                        .toBigDecimal()
                        .setScale(2, RoundingMode.HALF_EVEN)
                        .toDouble()
                    )
                }
            }

            reduce { state.copy(sumValue = calculateIndex(state)) }
        }
    }

    private fun onSendAnswer(questionNumber: QuestionNumber, value: Int) {
        intent {
            when (questionNumber) {
                QuestionNumber.ONE -> {
                    reduce { state.copy(question1 = value) }
                }
                QuestionNumber.TWO -> {
                    reduce { state.copy(question2 = value) }
                }
                QuestionNumber.THREE -> {
                    reduce { state.copy(question3 = value) }
                }
                QuestionNumber.FOUR -> {
                    reduce { state.copy(question4 = value) }
                }
            }

            reduce { state.copy(sumValue = calculateIndex(state)) }
        }
    }

    private fun calculateIndex(state: AsdasState): Double {
        return when (state.srbSoeType) {
            SrbSoeType.SRB -> {
                (0.12 * state.question1 + 0.06 * state.question2 + 0.11 * state.question3 + 0.07 * state.question4 + 0.58 * ln(
                    state.srbSoeValue + 1))
                    .toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN)
                    .toDouble()
            }
            SrbSoeType.SOE -> {
                (0.08 * state.question1 + 0.07 * state.question2 + 0.11 * state.question3 + 0.09 * state.question4 + 0.29 * sqrt(
                    state.srbSoeValue))
                    .toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN)
                    .toDouble()
            }
        }
    }

    private fun sendIndex() {
        intent {
            val body = AsdasIndexRequestBody(
                patientId = state.patientId,
                question1 = state.question1,
                question2 = state.question2,
                question3 = state.question3,
                question4 = state.question4,
                srbSoeType = state.srbSoeType,
                srbSoeValue = state.srbSoeValue,
                date = Date(),
                sumValue = state.sumValue
            )

            when (val result = asdasRepository.sendIndex(body)) {
                is RequestResult.Error -> {
                    postSideEffect(AsdasSideEffect.ShowToastMessage(result.message ?: ""))
                }
                is RequestResult.Success -> {
                    postSideEffect(AsdasSideEffect.ShowToastMessage("Индекс успешно пройден. Спасибо за потраченное время! :)"))
                    postSideEffect(AsdasSideEffect.Finish)
                }
            }
        }
    }
}