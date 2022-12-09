package ru.therapyapp.data_bvas.internal

import ru.therapyapp.data_bvas.model.BvasIndex
import ru.therapyapp.data_bvas.model.BvasRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface BvasService {
    @POST("api/v1/bvas")
    suspend fun sendIndex(@Body bvasIndexCreationBody: BvasRequestBody): BvasIndex

    @GET("api/v1/bvas")
    suspend fun getIndexes(): List<BvasIndex>

    @GET("api/v1/bvas/byPatient/{id}")
    suspend fun getIndexesByPatient(@Path("id") patientId: Int): List<BvasIndex>
}