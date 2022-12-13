package ru.therapyapp.data_questionnaire.internal

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.therapyapp.data_questionnaire.model.Questionnaire
import ru.therapyapp.data_questionnaire.model.QuestionnaireRequestBody

interface QuestionnaireService {
    @GET("api/v1/questionnaire")
    suspend fun getQuestionnaires(): List<Questionnaire>

    @POST("api/v1/questionnaire")
    suspend fun createQuestionnaires(@Body questionnaire: QuestionnaireRequestBody)

    @GET("api/v1/questionnaire/byDoctor/{id}")
    suspend fun getQuestionnairesByDoctor(@Path("id") doctorId: Int): List<Questionnaire>

    @GET("api/v1/questionnaire/byPatient/{id}")
    suspend fun getQuestionnairesByPatient(@Path("id") patientId: Int): List<Questionnaire>
}