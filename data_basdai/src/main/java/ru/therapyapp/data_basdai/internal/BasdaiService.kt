package ru.therapyapp.data_basdai.internal

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.therapyapp.data_basdai.model.BasdaiIndex
import ru.therapyapp.data_basdai.model.BasdaiIndexCreationBody

internal interface BasdaiService {
    @POST("api/v1/basdai")
    suspend fun sendIndex(@Body basdaiIndexCreationBody: BasdaiIndexCreationBody): BasdaiIndex

    @GET("api/v1/basdai")
    suspend fun getIndexes(): List<BasdaiIndex>

    @GET("api/v1/basdai/byPatient/{id}")
    suspend fun getIndexesByPatient(@Path("id") patientId: Int): List<BasdaiIndex>
}