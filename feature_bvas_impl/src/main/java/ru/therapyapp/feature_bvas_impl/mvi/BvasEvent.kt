package ru.therapyapp.feature_bvas_impl.mvi

import ru.therapyapp.data_bvas.model.BvasIndex

sealed class BvasEvent {
    data class OnAddBvasAnswer(
        val questionNumber: QuestionNumber,
        val questionAnswer: BvasIndex.QuestionAnswer,
    ) : BvasEvent()

    data class OnDeleteBvasAnswer(
        val questionNumber: QuestionNumber,
        val questionAnswer: BvasIndex.QuestionAnswer,
    ) : BvasEvent()

    object OnSendIndex : BvasEvent()

}
