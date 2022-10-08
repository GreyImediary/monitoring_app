package ru.therapyapp.data_doctor.api

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_doctor.api.entity.DoctorRequestBody

interface DoctorRepository {
    suspend fun createDoctor(doctorRequestBody: DoctorRequestBody): RequestResult<Doctor>
    suspend fun getDoctorByUserId(id: Int): RequestResult<Doctor>
}