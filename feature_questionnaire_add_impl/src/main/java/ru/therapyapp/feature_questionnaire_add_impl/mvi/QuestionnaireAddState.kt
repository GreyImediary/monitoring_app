package ru.therapyapp.feature_questionnaire_add_impl.mvi

import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire.model.QuestionnaireRequestBody

data class QuestionnaireAddState(
    val doctorId: Int = -1,
    val patients: List<Patient> = emptyList(),
    val questionnaire: QuestionnaireRequestBody = QuestionnaireRequestBody(
        name = "",
        doctorId = doctorId,
        forPatientId = null,
        questions = emptyList()
    )
)