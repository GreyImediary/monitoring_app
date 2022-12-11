package ru.therapyapp.feature_answered_questionnaire_impl.mvi

sealed class QuestionnaireAnsweredSideEffect {
    data class ShowMessage(val message: String) : QuestionnaireAnsweredSideEffect()
    object Finish : QuestionnaireAnsweredSideEffect()
}
