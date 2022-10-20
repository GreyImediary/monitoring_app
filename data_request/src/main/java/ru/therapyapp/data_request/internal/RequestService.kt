package ru.therapyapp.data_request.internal

import retrofit2.http.*
import ru.therapyapp.data_request.api.entity.Request
import ru.therapyapp.data_request.api.entity.RequestCreationBody
import ru.therapyapp.data_request.api.entity.RequestUpdateBody

internal interface RequestService {
    @POST("api/v1/request")
    suspend fun createRequest(@Body requestCreationBody: RequestCreationBody): List<Request>

    @PUT("api/v1/request")
    suspend fun updateRequest(@Body requestUpdateBody: RequestUpdateBody): List<Request>

    @GET("api/v1/request/byDoctor/{id}")
    suspend fun getDoctorRequests(@Path("id") doctorId: Int): List<Request>

    @GET("api/v1/request/byPatient/{id}")
    suspend fun getPatientRequests(@Path("id") patientId: Int): List<Request>

    @DELETE("api/v1/request/{id}")
    suspend fun deleteRequest(@Path("id") id: Int): List<Request>
}