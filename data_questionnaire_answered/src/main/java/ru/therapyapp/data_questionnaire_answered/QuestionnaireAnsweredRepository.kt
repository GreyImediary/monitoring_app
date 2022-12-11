package ru.therapyapp.data_questionnaire_answered

import retrofit2.http.Path
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnswered
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnsweredRequestBody

interface QuestionnaireAnsweredRepository {
    suspend fun createQuestionnaire(questionnaireAnsweredBody: QuestionnaireAnsweredRequestBody): RequestResult<Unit>

    suspend fun getQuestionnairesAnswered(): RequestResult<List<QuestionnaireAnswered>>

    suspend fun getQuestionnairesAnsweredById(patientId: Int): RequestResult<List<QuestionnaireAnswered>>

    suspend fun getQuestionnairesAnsweredByQuestionnaireId(questionnaireId: Int):  RequestResult<List<QuestionnaireAnswered>>
}