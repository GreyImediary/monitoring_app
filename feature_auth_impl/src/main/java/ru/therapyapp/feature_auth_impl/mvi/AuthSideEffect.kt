package ru.therapyapp.feature_auth_impl.mvi

import ru.therapyapp.data_core.entity.User
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_patient.api.entity.Patient

sealed class AuthSideEffect {
    data class OpenPatientScreen(val patient: Patient) : AuthSideEffect()
    data class OpenDoctorScreen(val doctor: Doctor) : AuthSideEffect()
    data class OpenUserDataScreen(val user: User) : AuthSideEffect()
    object ShowRegisterView : AuthSideEffect()
    data class ShowMessage(val text: String) : AuthSideEffect()
}