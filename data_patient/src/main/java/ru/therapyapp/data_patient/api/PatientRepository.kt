package ru.therapyapp.data_patient.api

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_patient.api.entity.PatientRequestBody

interface PatientRepository {
    suspend fun createPatient(patientRequestBody: PatientRequestBody): RequestResult<Patient>
    suspend fun getPatientByUserId(id: Int): RequestResult<Patient>
}