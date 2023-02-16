package ru.therapyapp.feature_sledai_impl.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_sledai.SelenaSledaiRepository
import ru.therapyapp.data_sledai.model.SelenaSledaiIndexBody
import java.util.Date

class SelenaSledaiViewModel(
    patientId: Int,
    private val sledaiRepository: SelenaSledaiRepository,
) : MviViewModel<SelenaSledaiEvent, SelenaSledaiState, SelenaSledaiSideEffect>(
    initialState = SelenaSledaiState(patientId)
) {
    override fun dispatch(event: SelenaSledaiEvent) {
        when (event) {
            is SelenaSledaiEvent.OnAddSledai -> addSledai(event.answer, event.point)
            is SelenaSledaiEvent.OnRemoveSledai -> removeSledai(event.answer, event.point)
            SelenaSledaiEvent.OnSendIndex -> sendIndex()
        }
    }

    private fun sendIndex() {
        intent {
            val body = SelenaSledaiIndexBody(
                patientId = state.patientId,
                answer = state.answer,
                sumValue = state.sumValue,
                date = Date()
            )
            when (val result = sledaiRepository.sendIndex(body)) {
                is RequestResult.Error -> {
                    postSideEffect(SelenaSledaiSideEffect.ShowToastMessage(result.message ?: "Не получилось отправить индекс"))
                }
                is RequestResult.Success -> {
                    postSideEffect(SelenaSledaiSideEffect.ShowToastMessage("Индекс успешно пройден. Спасибо за потраченное время! :)"))
                    postSideEffect(SelenaSledaiSideEffect.Finish)
                }
            }
        }
    }

    private fun removeSledai(answer: String, point: Int) {
        intent {
            val newAnswer = state.answer.replace("$answer; ", "")
            reduce { state.copy(answer = newAnswer, sumValue = state.sumValue - point) }
        }
    }

    private fun addSledai(answer: String, point: Int) {
        intent {
            val newAnswer = state.answer + "$answer; "
            reduce { state.copy(answer = newAnswer, sumValue = state.sumValue + point) }
        }
    }
}