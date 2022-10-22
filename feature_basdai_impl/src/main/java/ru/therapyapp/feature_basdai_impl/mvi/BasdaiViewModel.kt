package ru.therapyapp.feature_basdai_impl.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_basdai.BasdaiRepository
import ru.therapyapp.data_basdai.model.BasdaiIndexCreationBody
import java.math.RoundingMode
import java.util.*

class BasdaiViewModel(
    patientId: Int,
    private val basdaiRepository: BasdaiRepository,
) : MviViewModel<BasdaiEvent, BasdaiState, BasdaiSideEffect>(
    initialState = BasdaiState(patientId = patientId)) {
    override fun dispatch(event: BasdaiEvent) {
        when (event) {
            is BasdaiEvent.SendAnswer -> sendAnswer(event.questionNumber, event.value)
            BasdaiEvent.SendIndex -> sendIndex()
        }
    }

    private fun sendIndex() {
        intent {
            val body = BasdaiIndexCreationBody(
                patientId = state.patientId,
                question1Value = state.answerOne,
                question2Value = state.answerTwo,
                question3Value = state.answerThree,
                question4Value = state.answerFour,
                question5Value = state.answerFive,
                question6Value = state.answerSix,
                sumValue = state.sumValue,
                date = Date()
            )

            when (val result = basdaiRepository.sendIndex(body)) {
                is RequestResult.Error -> {
                    postSideEffect(BasdaiSideEffect.ShowToastMessage(result.message ?: ""))
                }
                is RequestResult.Success -> {
                    postSideEffect(BasdaiSideEffect.ShowToastMessage("Индекс успешно пройден. Спасибо за потраченное время! :)"))
                    postSideEffect(BasdaiSideEffect.Finish)
                }
            }
        }
    }

    private fun sendAnswer(questionNumber: QuestionNumber, value: Int) {
        intent {
            when (questionNumber) {
                QuestionNumber.ONE -> {
                    reduce { state.copy(answerOne = value) }
                }
                QuestionNumber.TWO -> {
                    reduce { state.copy(answerTwo = value) }

                }
                QuestionNumber.THREE -> {
                    reduce { state.copy(answerThree = value) }

                }
                QuestionNumber.FOUR -> {
                    reduce { state.copy(answerFour = value) }

                }
                QuestionNumber.FIVE -> {
                    reduce { state.copy(answerFive = value) }

                }
                QuestionNumber.SIX -> {
                    reduce { state.copy(answerSix = value) }
                }
            }

            reduce { state.copy(sumValue = calculateIndex(state)) }
        }
    }

    private fun calculateIndex(state: BasdaiState): Double {
        return ((state.answerOne + state.answerTwo + state.answerThree + state.answerFour + (state.answerFive + state.answerSix) / 2.0) / 5.0)
            .toBigDecimal()
            .setScale(2, RoundingMode.HALF_EVEN)
            .toDouble()
    }
}