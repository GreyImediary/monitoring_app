package ru.therapyapp.data_questionnaire.model

data class Questionnaire(
    val name: String,
    val forPatientId: Int?,
    val questions: List<Question>
)

data class Question(
    val title: String,
    val questionType: QuestionType,
    val options: List<Option>
)

data class Option(
    val description: String
)

enum class QuestionType {
    FIELD, CHECKBOXES, RADIOBUTTONS
}