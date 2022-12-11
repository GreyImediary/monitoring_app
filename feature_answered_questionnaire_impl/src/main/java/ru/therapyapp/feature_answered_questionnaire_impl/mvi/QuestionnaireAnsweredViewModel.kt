package ru.therapyapp.feature_answered_questionnaire_impl.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_questionnaire_answered.QuestionnaireAnsweredRepository

class QuestionnaireAnsweredViewModel(
    private val questionnaireId: Int,
    private val questionnaireAnsweredRepository: QuestionnaireAnsweredRepository,
) : MviViewModel<QuestionnaireAnsweredEvent, QuestionnaireAnsweredState, QuestionnaireAnsweredSideEffect>(
    QuestionnaireAnsweredState()
) {
    override fun dispatch(event: QuestionnaireAnsweredEvent) {
        when (event) {
            QuestionnaireAnsweredEvent.FetchData -> fetchData()
            QuestionnaireAnsweredEvent.OnArrowBackClick -> finish()
        }
    }

    private fun fetchData() {
        intent {
            when (val result = questionnaireAnsweredRepository.getQuestionnairesAnsweredByQuestionnaireId(questionnaireId)) {
                is RequestResult.Error -> {
                    postSideEffect(QuestionnaireAnsweredSideEffect.ShowMessage(result.message ?: "Ошибка при получении анкет"))
                }
                is RequestResult.Success -> {
                    reduce { state.copy(questionnairesAnswered = result.data) }
                }
            }
        }
    }

    private fun finish() {
        intent {
            postSideEffect(QuestionnaireAnsweredSideEffect.Finish)
        }
    }


}