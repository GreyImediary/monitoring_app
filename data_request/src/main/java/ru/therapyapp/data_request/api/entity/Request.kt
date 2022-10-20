package ru.therapyapp.data_request.api.entity

import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_patient.api.entity.Patient

class Request(
    val id: Int,
    val doctor: Doctor,
    val patient: Patient,
    val status: RequestStatus
)