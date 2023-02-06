package ru.therapyapp.data_mkb.internal

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.therapyapp.data_mkb.model.Mkb

internal interface MkbService {
    @GET("api/v1/mkb")
    suspend fun getMkbs(): List<Mkb>

    @POST("api/v1/mkb")
    suspend fun createMkb(@Body mkb: Mkb): List<Mkb>
}