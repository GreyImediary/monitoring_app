package ru.therapyapp.data_request.internal

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_request.api.RequestRepository
import ru.therapyapp.data_request.api.entity.Request
import ru.therapyapp.data_request.api.entity.RequestCreationBody
import ru.therapyapp.data_request.api.entity.RequestUpdateBody

internal class RequestRepositoryImpl(
    private val service: RequestService,
    private val dispatcher: CoroutineDispatcher,
) : RequestRepository {
    override suspend fun createRequest(requestCreationBody: RequestCreationBody): RequestResult<List<Request>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(service.createRequest(requestCreationBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun updateRequest(requestUpdateBody: RequestUpdateBody): RequestResult<List<Request>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(service.updateRequest(requestUpdateBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun deleteRequest(requestId: Int): RequestResult<List<Request>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(service.deleteRequest(requestId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getDoctorRequests(doctorId: Int): RequestResult<List<Request>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(service.getDoctorRequests(doctorId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getPatientRequests(patientId: Int): RequestResult<List<Request>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(service.getPatientRequests(patientId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}