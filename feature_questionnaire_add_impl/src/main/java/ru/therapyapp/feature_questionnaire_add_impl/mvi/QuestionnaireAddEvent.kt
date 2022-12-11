package ru.therapyapp.feature_questionnaire_add_impl.mvi

import ru.therapyapp.data_questionnaire.model.QuestionType

sealed class QuestionnaireAddEvent {
    data class ChangeName(val newName: String) : QuestionnaireAddEvent()
    object AddQuestion : QuestionnaireAddEvent()
    data class DeleteQuestion(val questionIndex: Int) : QuestionnaireAddEvent()
    data class ChangeQuestionType(val questionType: QuestionType, val questionIndex: Int) : QuestionnaireAddEvent()
    data class ChangeQuestionTitle(val newTitle: String,val questionIndex: Int) : QuestionnaireAddEvent()
    data class AddOption(val questionIndex: Int) : QuestionnaireAddEvent()
    data class DeleteOption(val questionIndex: Int, val optionIndex: Int) : QuestionnaireAddEvent()
    data class ChangeOptionDescription(val newDescription: String, val questionIndex: Int, val optionIndex: Int) : QuestionnaireAddEvent()
    data class ChangePatient(val patientId: Int?) : QuestionnaireAddEvent()
    object CreateQuestionnaire : QuestionnaireAddEvent()
    object OnArrowBackClick : QuestionnaireAddEvent()
}