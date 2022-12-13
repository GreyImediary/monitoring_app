package ru.therapyapp.feature_questionnaire_impl.mvi

sealed class QuestionnaireSideEffect {
    data class ShowMessage(val message: String) : QuestionnaireSideEffect()
    object Finish : QuestionnaireSideEffect()
}
