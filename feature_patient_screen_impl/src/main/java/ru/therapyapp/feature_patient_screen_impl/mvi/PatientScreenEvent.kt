package ru.therapyapp.feature_patient_screen_impl.mvi

import ru.therapyapp.data_request.api.entity.RequestUpdateBody

sealed class PatientScreenEvent {
    object FetchData : PatientScreenEvent()
    object OnBasdaiClick : PatientScreenEvent()
    object OnAsdasClick : PatientScreenEvent()
    object OnBvasClick : PatientScreenEvent()
    data class OnUpdateRequest(val updateRequestBody: RequestUpdateBody) : PatientScreenEvent()
    data class OnQuestionnaireClick(val questionnaireId: Int) : PatientScreenEvent()
    object Logout : PatientScreenEvent()
}