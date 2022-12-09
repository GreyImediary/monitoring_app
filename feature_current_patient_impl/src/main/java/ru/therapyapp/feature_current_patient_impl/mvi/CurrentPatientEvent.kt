package ru.therapyapp.feature_current_patient_impl.mvi

import java.util.Date

sealed class CurrentPatientEvent {
    object FetchData : CurrentPatientEvent()
    object OnBackClick : CurrentPatientEvent()
    object OnDeleteDateRange : CurrentPatientEvent()
    data class ChangeIndex(val chosenIndex: IndexType) : CurrentPatientEvent()
    data class ChangeDataPeriod(val startDate: Long, val endDate: Long) : CurrentPatientEvent()
    data class SelectIndexDataByDate(val date: Date?) : CurrentPatientEvent()
    data class AddComment(val comment: String) : CurrentPatientEvent()
    data class OnMessageShow(val message: String) : CurrentPatientEvent()
}