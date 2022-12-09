package ru.therapyapp.data_questionnaire.internal

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.therapyapp.data_questionnaire.model.Questionnaire

interface QuestionnaireService {
    @GET("api/v1/questionnaire")
    suspend fun getQuestionnaires(): List<Questionnaire>

    @POST("api/v1/questionnaire")
    suspend fun createQuestionnaires(@Body questionnaire: Questionnaire)

}