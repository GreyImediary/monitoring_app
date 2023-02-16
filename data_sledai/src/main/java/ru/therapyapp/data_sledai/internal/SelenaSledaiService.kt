package ru.therapyapp.data_sledai.internal

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.therapyapp.data_sledai.model.SelenaSledaiIndex
import ru.therapyapp.data_sledai.model.SelenaSledaiIndexBody

internal interface SelenaSledaiService {
    @POST("api/v1/sledai")
    suspend fun sendIndex(@Body selenaSledaiBody: SelenaSledaiIndexBody): SelenaSledaiIndex

    @GET("api/v1/sledai")
    suspend fun getIndexes(): List<SelenaSledaiIndex>

    @GET("api/v1/sledai/byPatient/{id}")
    suspend fun getIndexesByPatient(@Path("id") patientId: Int): List<SelenaSledaiIndex>
}