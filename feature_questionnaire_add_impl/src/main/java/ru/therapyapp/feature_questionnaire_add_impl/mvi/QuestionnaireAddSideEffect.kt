package ru.therapyapp.feature_questionnaire_add_impl.mvi

sealed class QuestionnaireAddSideEffect {
    data class ShowMessage(val message: String) : QuestionnaireAddSideEffect()
    object Finish : QuestionnaireAddSideEffect()
}
