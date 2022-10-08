package ru.therapyapp.data_patient.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_patient.api.PatientRepository
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_patient.api.entity.PatientRequestBody

internal class PatientRepositoryImpl(
    private val patientService: PatientService,
    private val dispatcher: CoroutineDispatcher
) : PatientRepository
{
    override suspend fun createPatient(patientRequestBody: PatientRequestBody): RequestResult<Patient> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(patientService.createPatient(patientRequestBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getPatientByUserId(id: Int): RequestResult<Patient> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(patientService.getDoctorByUserId(id))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}