package ru.therapyapp.data_doctor.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_doctor.api.DoctorRepository
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_doctor.api.entity.DoctorRequestBody

internal class DoctorRepositoryImpl(
    private val doctorService: DoctorService,
    private val dispatcher: CoroutineDispatcher
) : DoctorRepository {
    override suspend fun createDoctor(doctorRequestBody: DoctorRequestBody): RequestResult<Doctor> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(doctorService.createDoctor(doctorRequestBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getDoctorByUserId(id: Int): RequestResult<Doctor> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(doctorService.getDoctorByUserId(id))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}