package ru.therapyapp.feature_doctor_screen_impl.mvi

import ru.therapyapp.data_patient.api.entity.Patient

sealed class DoctorScreenSideEffect {
    data class ShowToast(val message: String) : DoctorScreenSideEffect()
    data class OpenPatientDataScreen(val patient: Patient) : DoctorScreenSideEffect()
}