package ru.therapyapp.feature_doctor_screen_impl.mvi

import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_request.api.entity.Request

data class DoctorScreenState(
    val doctor: Doctor?,
    val requests: List<Request> = listOf(),
    val allPatients: List<Patient> = listOf(),
    val isRefreshing: Boolean = false,
    val isRequestCreationDialogOpened: Boolean = false,
)