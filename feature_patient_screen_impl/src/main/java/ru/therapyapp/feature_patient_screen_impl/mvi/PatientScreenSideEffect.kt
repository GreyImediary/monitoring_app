package ru.therapyapp.feature_patient_screen_impl.mvi

sealed class PatientScreenSideEffect {
    data class ShowToast(val message: String) : PatientScreenSideEffect()
    object OpenBasdaiScreen : PatientScreenSideEffect()
}
