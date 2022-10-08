package ru.therapyapp.data_doctor.internal

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_doctor.api.entity.DoctorRequestBody

internal interface DoctorService {
    @POST("api/v1/doctor")
    suspend fun createDoctor(@Body doctorRequestBody: DoctorRequestBody): Doctor

    @GET("api/v1/doctor/{userId}")
    suspend fun getDoctorByUserId(@Path("userId") id: Int): Doctor
}