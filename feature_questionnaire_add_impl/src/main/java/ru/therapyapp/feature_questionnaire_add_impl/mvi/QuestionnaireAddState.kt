package ru.therapyapp.feature_questionnaire_add_impl.mvi

import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire.model.Questionnaire

data class QuestionnaireAddState(
    val doctorId: Int = -1,
    val patients: List<Patient> = emptyList(),
    val questionnaire: Questionnaire = Questionnaire(
        name = "",
        doctorId = doctorId,
        forPatientId = null,
        questions = emptyList()
    )
)