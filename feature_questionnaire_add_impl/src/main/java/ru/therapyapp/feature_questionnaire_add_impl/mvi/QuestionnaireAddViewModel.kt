package ru.therapyapp.feature_questionnaire_add_impl.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire.QuestionnaireRepository
import ru.therapyapp.data_questionnaire.model.Option
import ru.therapyapp.data_questionnaire.model.Question
import ru.therapyapp.data_questionnaire.model.QuestionType

class QuestionnaireAddViewModel(
    doctorId: Int,
    patients: List<Patient>,
    private val questionnaireRepository: QuestionnaireRepository,
) : MviViewModel<QuestionnaireAddEvent, QuestionnaireAddState, QuestionnaireAddSideEffect>(
    QuestionnaireAddState(doctorId, patients)
) {
    override fun dispatch(event: QuestionnaireAddEvent) {
        when (event) {
            is QuestionnaireAddEvent.AddOption -> addOption(event.questionIndex)
            QuestionnaireAddEvent.AddQuestion -> addQuestion()
            is QuestionnaireAddEvent.ChangeName -> changeName(event.newName)
            is QuestionnaireAddEvent.ChangeOptionDescription -> changeOptionDescription(event.newDescription, event.questionIndex, event.optionIndex)
            is QuestionnaireAddEvent.ChangePatient -> changePatient(event.patientId)
            is QuestionnaireAddEvent.ChangeQuestionTitle -> changeQuestionTitle(event.newTitle, event.questionIndex)
            is QuestionnaireAddEvent.ChangeQuestionType -> changeQuestionType(event.questionType, event.questionIndex)
            QuestionnaireAddEvent.CreateQuestionnaire -> createQuestionnaire()
            is QuestionnaireAddEvent.DeleteOption -> deleteOption(event.questionIndex, event.optionIndex)
            is QuestionnaireAddEvent.DeleteQuestion -> deleteQuestion(event.questionIndex)
            QuestionnaireAddEvent.OnArrowBackClick -> finish()
        }
    }

    private fun finish() {
        intent {
            postSideEffect(QuestionnaireAddSideEffect.Finish)
        }
    }

    private fun createQuestionnaire() {
        intent {
            when (val result = questionnaireRepository.createQuestionnaires(state.questionnaire)) {
                is RequestResult.Error -> {
                    postSideEffect(QuestionnaireAddSideEffect.ShowMessage(result.message ?: "Ошикба при создании анкеты, проверьте данные"))
                }
                is RequestResult.Success -> {
                    postSideEffect(QuestionnaireAddSideEffect.ShowMessage("Анкета успешно создана"))
                    postSideEffect(QuestionnaireAddSideEffect.Finish)
                }
            }
        }
    }

    private fun changeOptionDescription(
        newDescription: String,
        questionIndex: Int,
        optionIndex: Int,
    ) {
        intent {
            val mutableQuestions = state.questionnaire.questions.toMutableList()
            val question = mutableQuestions[questionIndex]
            val mutableOptions = question.options.toMutableList()

            val option = mutableOptions[optionIndex]
            val newOption = option.copy(description = newDescription)
            mutableOptions[optionIndex] = newOption

            val newQuestion = question.copy(options = mutableOptions)
            mutableQuestions[questionIndex] = newQuestion

            val newQuestionnaire = state.questionnaire.copy(questions = mutableQuestions)

            reduce { state.copy(questionnaire = newQuestionnaire) }
        }
    }

    private fun deleteOption(questionIndex: Int, optionIndex: Int) {
        intent {
            val mutableQuestions = state.questionnaire.questions.toMutableList()
            val question = mutableQuestions[questionIndex]
            val mutableOptions = question.options.toMutableList()
            mutableOptions.removeAt(optionIndex)

            val newQuestion = question.copy(options = mutableOptions)
            mutableQuestions[questionIndex] = newQuestion

            val newQuestionnaire = state.questionnaire.copy(questions = mutableQuestions)

            reduce { state.copy(questionnaire = newQuestionnaire) }
        }
    }

    private fun deleteQuestion(questionIndex: Int) {
        intent {
            val mutableQuestions = state.questionnaire.questions.toMutableList()
            mutableQuestions.removeAt(questionIndex)
            val newQuestionnaire = state.questionnaire.copy(questions = mutableQuestions)

            reduce { state.copy(questionnaire = newQuestionnaire) }
        }
    }

    private fun changeQuestionType(questionType: QuestionType, questionIndex: Int) {
        intent {
            val mutableQuestions = state.questionnaire.questions.toMutableList()
            val question = mutableQuestions[questionIndex]
            val newQuestion = question.copy(questionType = questionType)
            mutableQuestions[questionIndex] = newQuestion

            val newQuestionnaire = state.questionnaire.copy(questions = mutableQuestions)

            reduce { state.copy(questionnaire = newQuestionnaire) }
        }
    }

    private fun changeQuestionTitle(newTitle: String, questionIndex: Int) {
        intent {
            val mutableQuestions = state.questionnaire.questions.toMutableList()
            val question = mutableQuestions[questionIndex]
            val newQuestion = question.copy(title = newTitle)
            mutableQuestions[questionIndex] = newQuestion

            val newQuestionnaire = state.questionnaire.copy(questions = mutableQuestions)

            reduce { state.copy(questionnaire = newQuestionnaire) }
        }
    }

    private fun addOption(questionIndex: Int) {
        intent {
            val mutableQuestions = state.questionnaire.questions.toMutableList()

            val question = state.questionnaire.questions[questionIndex]
            val newQuestion = question.copy(options = question.options + Option(""))

            mutableQuestions[questionIndex] = newQuestion

            val newQuestionnaire = state.questionnaire.copy(questions = mutableQuestions)

            reduce { state.copy(questionnaire = newQuestionnaire) }
        }
    }

    private fun addQuestion() {
        intent {
            val newQuestionnaire = state.questionnaire.copy(
                questions = state.questionnaire.questions +
                        Question("", QuestionType.FIELD, emptyList())
            )

            reduce { state.copy(questionnaire = newQuestionnaire) }
        }
    }

    private fun changePatient(patientId: Int?) {
        intent {
            val newQuestionnaire = state.questionnaire.copy(forPatientId = patientId)
            reduce { state.copy(questionnaire = newQuestionnaire) }
        }
    }

    private fun changeName(newName: String) {
        intent {
            val newQuestionnaire = state.questionnaire.copy(name = newName)
            reduce { state.copy(questionnaire = newQuestionnaire) }
        }
    }
}