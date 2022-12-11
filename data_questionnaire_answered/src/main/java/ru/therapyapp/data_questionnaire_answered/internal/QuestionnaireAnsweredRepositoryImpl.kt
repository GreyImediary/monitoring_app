package ru.therapyapp.data_questionnaire_answered.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_questionnaire_answered.QuestionnaireAnsweredRepository
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnswered
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnsweredRequestBody

class QuestionnaireAnsweredRepositoryImpl(
    private val questionnaireAnsweredService: QuestionnaireAnsweredService,
    private val dispatcher: CoroutineDispatcher,
) : QuestionnaireAnsweredRepository {
    override suspend fun createQuestionnaire(questionnaireAnsweredBody: QuestionnaireAnsweredRequestBody): RequestResult<Unit> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(questionnaireAnsweredService.createQuestionnaire(questionnaireAnsweredBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getQuestionnairesAnswered(): RequestResult<List<QuestionnaireAnswered>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(questionnaireAnsweredService.getQuestionnairesAnswered())
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getQuestionnairesAnsweredById(patientId: Int): RequestResult<List<QuestionnaireAnswered>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(questionnaireAnsweredService.getQuestionnairesAnsweredByPatientId(patientId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getQuestionnairesAnsweredByQuestionnaireId(questionnaireId: Int): RequestResult<List<QuestionnaireAnswered>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(questionnaireAnsweredService.getQuestionnairesAnsweredByQuestionnaireId(questionnaireId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}