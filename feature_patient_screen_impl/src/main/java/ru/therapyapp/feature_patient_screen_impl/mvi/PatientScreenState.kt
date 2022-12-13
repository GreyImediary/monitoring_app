package ru.therapyapp.feature_patient_screen_impl.mvi

import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire.model.Questionnaire
import ru.therapyapp.data_request.api.entity.Request

data class PatientScreenState(
    val patient: Patient? = null,
    val requests: List<Request> = listOf(),
    val isRefreshing: Boolean = false,
    val questionnaires: List<Questionnaire> = emptyList()
)