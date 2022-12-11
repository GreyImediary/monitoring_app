package ru.therapyapp.data_questionnaire.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_questionnaire.QuestionnaireRepository
import ru.therapyapp.data_questionnaire.model.Questionnaire

class QuestionnaireRepositoryImpl(
    private val questionnaireService: QuestionnaireService,
    private val dispatcher: CoroutineDispatcher,
    ) : QuestionnaireRepository {
    override suspend fun getQuestionnaires(): RequestResult<List<Questionnaire>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(questionnaireService.getQuestionnaires())
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun createQuestionnaires(questionnaire: Questionnaire): RequestResult<Unit> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(questionnaireService.createQuestionnaires(questionnaire))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getQuestionnairesByDoctor(doctorId: Int): RequestResult<List<Questionnaire>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(questionnaireService.getQuestionnairesByDoctor(doctorId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}