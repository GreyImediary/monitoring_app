package ru.therapyapp.feature_user_data_impl.screen.mvi

import ru.therapyapp.data_doctor.api.entity.DoctorRequestBody
import ru.therapyapp.data_patient.api.entity.PatientRequestBody

sealed class UserDataEvent {
    object FetchData : UserDataEvent()
    data class OnDoctorDone(val doctorRequestBody: DoctorRequestBody) : UserDataEvent()
    data class OnPatientDone(val patientRequestBody: PatientRequestBody) : UserDataEvent()

}