package com.example.feature_bvas_impl.mvi

import com.example.data_bvas.BvasRepository
import com.example.data_bvas.model.BvasIndex
import com.example.data_bvas.model.BvasRequestBody
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import java.util.*

class BvasViewModel(
    patientId: Int,
    private val bvasRepository: BvasRepository,
) : MviViewModel<BvasEvent, BvasState, BvasSideEffect>(initialState = BvasState(patientId = patientId)) {
    override fun dispatch(event: BvasEvent) {
        when (event) {
            is BvasEvent.OnAddBvasAnswer -> addBvasAnswer(event.questionAnswer, event.questionNumber)
            is BvasEvent.OnDeleteBvasAnswer -> removeBvasAnswer(event.questionAnswer, event.questionNumber)
            BvasEvent.OnSendIndex -> sendIndex()
        }
    }

    private fun sendIndex() {
        intent {
            val body = BvasRequestBody(
                patientId = state.patientId,
                date = Date(),
                question1 = state.question1,
                question2 = state.question2,
                question3 = state.question3,
                question4 = state.question4,
                question5 = state.question5,
                question6 = state.question6,
                question7 = state.question7,
                question8 = state.question8,
                question9 = state.question9,
                sumValue = state.sumValue,
            )

            when (val result = bvasRepository.sendIndex(body)) {
                is RequestResult.Error -> {
                    postSideEffect(BvasSideEffect.ShowToastMessage(result.message ?: ""))
                }
                is RequestResult.Success -> {
                    postSideEffect(BvasSideEffect.ShowToastMessage("Индекс успешно пройден. Спасибо за потраченное время! :)"))
                    postSideEffect(BvasSideEffect.Finish)
                }
            }
        }
    }

    private fun addBvasAnswer(
        questionAnswer: BvasIndex.QuestionAnswer,
        questionNumber: QuestionNumber,
    ) {
        intent {
            when (questionNumber) {
                QuestionNumber.ONE -> {
                    reduce { state.copy(question1 = state.question1 + questionAnswer) }
                }
                QuestionNumber.TWO -> {
                    reduce { state.copy(question2 = state.question2 + questionAnswer) }
                }
                QuestionNumber.THREE -> {
                    reduce { state.copy(question3 = state.question3 + questionAnswer) }
                }
                QuestionNumber.FOUR -> {
                    reduce { state.copy(question4 = state.question4 + questionAnswer) }
                }
                QuestionNumber.FIVE -> {
                    reduce { state.copy(question5 = state.question5 + questionAnswer) }
                }
                QuestionNumber.SIX -> {
                    reduce { state.copy(question6 = state.question6 + questionAnswer) }
                }
                QuestionNumber.SEVEN -> {
                    reduce { state.copy(question7 = state.question7 + questionAnswer) }
                }
                QuestionNumber.EIGHT -> {
                    reduce { state.copy(question8 = state.question8 + questionAnswer) }
                }
                QuestionNumber.NINE -> {
                    reduce { state.copy(question9 = state.question9 + questionAnswer) }
                }
            }

            reduce { state.copy(sumValue = calculateIndex(state)) }

        }
    }

    private fun removeBvasAnswer(
        questionAnswer: BvasIndex.QuestionAnswer,
        questionNumber: QuestionNumber,
    ) {
        intent {
            when (questionNumber) {
                QuestionNumber.ONE -> {
                    reduce { state.copy(question1 = state.question1 - questionAnswer) }
                }
                QuestionNumber.TWO -> {
                    reduce { state.copy(question2 = state.question2 - questionAnswer) }
                }
                QuestionNumber.THREE -> {
                    reduce { state.copy(question3 = state.question3 - questionAnswer) }
                }
                QuestionNumber.FOUR -> {
                    reduce { state.copy(question4 = state.question4 - questionAnswer) }
                }
                QuestionNumber.FIVE -> {
                    reduce { state.copy(question5 = state.question5 - questionAnswer) }
                }
                QuestionNumber.SIX -> {
                    reduce { state.copy(question6 = state.question6 - questionAnswer) }
                }
                QuestionNumber.SEVEN -> {
                    reduce { state.copy(question7 = state.question7 - questionAnswer) }
                }
                QuestionNumber.EIGHT -> {
                    reduce { state.copy(question8 = state.question8 - questionAnswer) }
                }
                QuestionNumber.NINE -> {
                    reduce { state.copy(question9 = state.question9 - questionAnswer) }
                }
            }

            reduce { state.copy(sumValue = calculateIndex(state)) }
        }
    }

    private fun calculateIndex(state: BvasState): Int {
        return (state.question1.maxOfOrNull { it.value } ?: 0) +
                (state.question2.maxOfOrNull { it.value } ?: 0) +
                (state.question3.maxOfOrNull { it.value } ?: 0) +
                (state.question4.maxOfOrNull { it.value } ?: 0) +
                (state.question5.maxOfOrNull { it.value } ?: 0) +
                (state.question6.maxOfOrNull { it.value } ?: 0) +
                (state.question7.maxOfOrNull { it.value } ?: 0) +
                (state.question8.maxOfOrNull { it.value } ?: 0) +
                (state.question9.maxOfOrNull { it.value } ?: 0)
    }

}