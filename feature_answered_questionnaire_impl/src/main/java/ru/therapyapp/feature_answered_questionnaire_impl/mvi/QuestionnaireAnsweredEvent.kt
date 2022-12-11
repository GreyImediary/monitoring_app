package ru.therapyapp.feature_answered_questionnaire_impl.mvi

sealed class QuestionnaireAnsweredEvent {
    object FetchData : QuestionnaireAnsweredEvent()
    object OnArrowBackClick : QuestionnaireAnsweredEvent()
}