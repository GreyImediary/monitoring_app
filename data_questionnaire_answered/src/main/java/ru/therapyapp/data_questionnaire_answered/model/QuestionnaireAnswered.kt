package ru.therapyapp.data_questionnaire_answered.model

import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire.model.Questionnaire
import java.util.Date

data class QuestionnaireAnswered(
    val patient: Patient,
    val questionnaire: Questionnaire,
    val date: Date,
    val answers: List<QuestionAnswer>
)

data class QuestionnaireAnsweredRequestBody(
    val patientId: Int,
    val questionnaireId: Int,
    val date: Date,
    val answers: List<QuestionAnswer>
)


data class QuestionAnswer(
    val title: String,
    val answer: String
)