package ru.therapyapp.feature_current_patient_impl.mvi

sealed class CurrentPatientEvent {
    object FetchData : CurrentPatientEvent()
    object OnBackClick : CurrentPatientEvent()
    object OnDeleteDateRange : CurrentPatientEvent()
    data class ChangeIndex(val chosenIndex: IndexType) : CurrentPatientEvent()
    data class ChangeDataPeriod(val startDate: String, val endDate: String) : CurrentPatientEvent()
    data class AddComment(val comment: String) : CurrentPatientEvent()
    data class OnMessageShow(val message: String) : CurrentPatientEvent()
}