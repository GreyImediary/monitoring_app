package ru.therapyapp.feature_doctor_screen_impl.mvi

import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_request.api.entity.RequestCreationBody

sealed class DoctorScreenEvent {
    object FetchData : DoctorScreenEvent()
    object OnRequestAddClick : DoctorScreenEvent()
    object OnRequestDialogDismissClick : DoctorScreenEvent()
    data class DeleteRequest(val requestId: Int) : DoctorScreenEvent()
    data class CreateRequest(val requestCreationBody: RequestCreationBody) : DoctorScreenEvent()
    data class OnPatientClick(val patient: Patient) : DoctorScreenEvent()
}