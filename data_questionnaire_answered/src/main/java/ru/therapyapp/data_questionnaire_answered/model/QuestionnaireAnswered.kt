package ru.therapyapp.data_questionnaire_answered.model

import ru.therapyapp.data_questionnaire.model.Questionnaire

data class QuestionnaireAnswered(
    val patientId: Int,
    val questionnaire: Questionnaire,
    val date: String,
    val answers: List<QuestionAnswer>
)

data class QuestionnaireAnsweredRequestBody(
    val patientId: Int,
    val questionnaireId: Int,
    val date: String,
    val answers: List<QuestionAnswer>
)


data class QuestionAnswer(
    val title: String,
    val answer: String
)