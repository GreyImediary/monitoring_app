package com.example.data_asdas.internal

import com.example.data_asdas.model.AsdasIndex
import com.example.data_asdas.model.AsdasIndexRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface AsdasService {

    @POST("api/v1/asdas")
    suspend fun sendIndex(@Body requestBody: AsdasIndexRequestBody): AsdasIndex

    @GET("api/v1/asdas")
    suspend fun getIndexes(): List<AsdasIndex>

    @GET("api/v1/asdas/byPatient/{id}")
    suspend fun getIndexesByPatient(@Path("id") patientId: Int): List<AsdasIndex>
}