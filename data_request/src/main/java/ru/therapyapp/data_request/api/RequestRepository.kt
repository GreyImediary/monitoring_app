package ru.therapyapp.data_request.api

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_request.api.entity.Request
import ru.therapyapp.data_request.api.entity.RequestCreationBody
import ru.therapyapp.data_request.api.entity.RequestUpdateBody

interface RequestRepository {
    suspend fun createRequest(requestCreationBody: RequestCreationBody): RequestResult<List<Request>>
    suspend fun updateRequest(requestUpdateBody: RequestUpdateBody): RequestResult<List<Request>>
    suspend fun getDoctorRequests(doctorId: Int): RequestResult<List<Request>>
    suspend fun getPatientRequests(patientId: Int): RequestResult<List<Request>>
    suspend fun deleteRequest(requestId: Int): RequestResult<List<Request>>
}