package ru.therapyapp.feature_questionnaire_impl.mvi

sealed class QuestionnaireEvent {
    object FetchData : QuestionnaireEvent()
    data class SetAnswer(val questionIndex: Int, val answer: String) : QuestionnaireEvent()
    data class AddCheckBoxAnswer(val questionIndex: Int, val answer: String) : QuestionnaireEvent()
    data class DeleteCheckBoxAnswer(val questionIndex: Int, val answer: String) : QuestionnaireEvent()
    object SendQuestionnaire : QuestionnaireEvent()
    object OnBackClick : QuestionnaireEvent()
}