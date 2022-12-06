package ru.therapyapp.feature_current_patient_impl.mvi

sealed class CurrentPatientSideEffect {
    object Finish : CurrentPatientSideEffect()
    data class ShowMessage(val message: String) : CurrentPatientSideEffect()
}
