package ru.therapyapp.data_patient.internal

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_patient.api.entity.PatientRequestBody

internal interface PatientService {
    @POST("api/v1/patient")
    suspend fun createPatient(@Body patientRequestBody: PatientRequestBody): Patient

    @GET("api/v1/patient")
    suspend fun getAllPatients(): List<Patient>

    @GET("api/v1/patient/{userId}")
    suspend fun getDoctorByUserId(@Path("userId") id: Int): Patient


}