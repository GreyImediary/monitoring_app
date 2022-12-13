package ru.therapyapp.feature_questionnaire_impl.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_questionnaire.QuestionnaireRepository
import ru.therapyapp.data_questionnaire_answered.QuestionnaireAnsweredRepository
import ru.therapyapp.data_questionnaire_answered.model.QuestionAnswer
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnsweredRequestBody
import java.util.*

class QuestionnaireViewModel(
    private val patientId: Int,
    private val questionnaireId: Int,
    private val questionnaireRepository: QuestionnaireRepository,
    private val questionnaireAnsweredRepository: QuestionnaireAnsweredRepository,
) : MviViewModel<QuestionnaireEvent, QuestionnaireState, QuestionnaireSideEffect>(
    initialState = QuestionnaireState(patientId, questionnaireId)
) {
    override fun dispatch(event: QuestionnaireEvent) {
        when (event) {
            is QuestionnaireEvent.AddCheckBoxAnswer -> addCheckBoxAnswer(event.questionIndex, event.answer)
            is QuestionnaireEvent.DeleteCheckBoxAnswer -> deleteCheckBoxAnswer(event.questionIndex, event.answer)
            QuestionnaireEvent.FetchData -> fetchData()
            QuestionnaireEvent.SendQuestionnaire -> sendQuestionnaire()
            is QuestionnaireEvent.SetAnswer -> setAnswer(event.questionIndex, event.answer)
            QuestionnaireEvent.OnBackClick -> backClick()
        }
    }

    private fun backClick() {
        intent {
            postSideEffect(QuestionnaireSideEffect.Finish)
        }
    }

    private fun sendQuestionnaire() {
        intent {
            when(val result = questionnaireAnsweredRepository.createQuestionnaire(state.questionnaireAnsweredBody)) {
                is RequestResult.Error -> {
                    postSideEffect(QuestionnaireSideEffect.ShowMessage(result.message ?: "Не удалось отправить анкету"))
                }
                is RequestResult.Success -> {
                    postSideEffect(QuestionnaireSideEffect.ShowMessage("Спасибо за выполнение анкеты! :)"))
                    postSideEffect(QuestionnaireSideEffect.Finish)
                }
            }
        }
    }

    private fun deleteCheckBoxAnswer(questionIndex: Int, answer: String) {
        intent {
            val mutableAnswers = state.questionnaireAnsweredBody.answers.toMutableList()
            val currentAnswer = mutableAnswers[questionIndex]
            val newAnswerText = currentAnswer.answer.replace("$answer; ", "")

            mutableAnswers[questionIndex] = currentAnswer.copy(answer = newAnswerText)

            val newBody = state.questionnaireAnsweredBody.copy(answers = mutableAnswers)

            reduce { state.copy(questionnaireAnsweredBody = newBody) }
        }
    }

    private fun addCheckBoxAnswer(questionIndex: Int, answer: String) {
        intent {
            val mutableAnswers = state.questionnaireAnsweredBody.answers.toMutableList()
            val currentAnswer = mutableAnswers[questionIndex]
            val newAnswerText = currentAnswer.answer + "$answer; "

            mutableAnswers[questionIndex] = currentAnswer.copy(answer = newAnswerText)

            val newBody = state.questionnaireAnsweredBody.copy(answers = mutableAnswers)

            reduce { state.copy(questionnaireAnsweredBody = newBody) }
        }
    }

    private fun setAnswer(questionIndex: Int, answer: String) {
        intent {
            val mutableAnswers = state.questionnaireAnsweredBody.answers.toMutableList()
            val newAnswer = mutableAnswers[questionIndex].copy(answer = answer)
            mutableAnswers[questionIndex] = newAnswer

            val newBody = state.questionnaireAnsweredBody.copy(answers = mutableAnswers)

            reduce { state.copy(questionnaireAnsweredBody = newBody) }
        }
    }

    private fun fetchData() {
        intent {
            when (val result = questionnaireRepository.getQuestionnaires()) {
                is RequestResult.Error -> {
                    postSideEffect(QuestionnaireSideEffect.ShowMessage("Не удалось найти анкету"))
                }
                is RequestResult.Success -> {
                    val currentQuestionnaire = result.data.find { it.id == questionnaireId }

                    currentQuestionnaire?.let { questionnaire ->
                        val answeredQuestionnaire = QuestionnaireAnsweredRequestBody(
                            patientId = patientId,
                            questionnaireId = questionnaire.id,
                            date = Date(),
                            answers = questionnaire.questions.map { question -> QuestionAnswer(question.title, "") }
                        )

                        reduce { state.copy(
                            questionnaire = questionnaire,
                            questionnaireAnsweredBody = answeredQuestionnaire
                        ) }
                    } ?: postSideEffect(QuestionnaireSideEffect.ShowMessage("Не удалось найти анкету"))

                }
            }
        }
    }
}