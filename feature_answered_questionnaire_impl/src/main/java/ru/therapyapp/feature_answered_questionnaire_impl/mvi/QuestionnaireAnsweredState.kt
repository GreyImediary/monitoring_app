package ru.therapyapp.feature_answered_questionnaire_impl.mvi

import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnswered

data class QuestionnaireAnsweredState(
    val questionnairesAnswered: List<QuestionnaireAnswered> = emptyList()
)