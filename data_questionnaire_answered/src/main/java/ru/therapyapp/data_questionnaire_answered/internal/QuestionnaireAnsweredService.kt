package ru.therapyapp.data_questionnaire_answered.internal

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnswered
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnsweredRequestBody

interface QuestionnaireAnsweredService {
    @POST("api/v1/questionnaireAnswered")
    suspend fun createQuestionnaire(@Body questionnaireAnsweredBody: QuestionnaireAnsweredRequestBody)

    @GET("api/v1/questionnaireAnswered")
    suspend fun getQuestionnairesAnswered(): List<QuestionnaireAnswered>

    @GET("api/v1/questionnaireAnswered/byPatient/{id}")
    suspend fun getQuestionnairesAnsweredById(@Path("id") patientId: Int): List<QuestionnaireAnswered>
}