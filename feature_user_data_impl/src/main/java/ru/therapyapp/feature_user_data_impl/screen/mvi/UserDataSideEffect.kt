package ru.therapyapp.feature_user_data_impl.screen.mvi

import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_patient.api.entity.Patient

sealed class UserDataSideEffect {
    data class OpenPatientScreen(val patient: Patient) : UserDataSideEffect()
    data class OpenDoctorScreen(val doctor: Doctor) : UserDataSideEffect()
    data class ShowToastMessage(val message: String) : UserDataSideEffect()
    object Finish : UserDataSideEffect()
}
