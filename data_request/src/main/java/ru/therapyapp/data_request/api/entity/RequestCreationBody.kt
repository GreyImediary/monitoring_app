package ru.therapyapp.data_request.api.entity

data class RequestCreationBody(
    val doctorId: Int,
    val patientId: Int,
    val status: RequestStatus,
)