package ru.therapyapp.feature_questionnaire_impl.mvi

import ru.therapyapp.data_questionnaire.model.Questionnaire
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnsweredRequestBody
import java.util.*

data class QuestionnaireState(
    val patientId: Int,
    val questionnaireId: Int,
    val questionnaire: Questionnaire? = null,
    val questionnaireAnsweredBody: QuestionnaireAnsweredRequestBody = QuestionnaireAnsweredRequestBody(
        patientId = -1,
        questionnaireId = -1,
        date = Date(),
        answers = emptyList()
    )
)
