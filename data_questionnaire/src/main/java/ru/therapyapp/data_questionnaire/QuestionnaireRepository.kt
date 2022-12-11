package ru.therapyapp.data_questionnaire

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_questionnaire.model.Questionnaire
import ru.therapyapp.data_questionnaire.model.QuestionnaireRequestBody

interface QuestionnaireRepository {
    suspend fun getQuestionnaires(): RequestResult<List<Questionnaire>>

    suspend fun createQuestionnaires(questionnaire: QuestionnaireRequestBody): RequestResult<Unit>

    suspend fun getQuestionnairesByDoctor(doctorId: Int): RequestResult<List<Questionnaire>>

}