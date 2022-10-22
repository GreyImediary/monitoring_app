package ru.therapyapp.feature_basdai_impl.mvi

sealed class BasdaiEvent {
    data class SendAnswer(val questionNumber: QuestionNumber, val value: Int) : BasdaiEvent()
    object SendIndex : BasdaiEvent()
}