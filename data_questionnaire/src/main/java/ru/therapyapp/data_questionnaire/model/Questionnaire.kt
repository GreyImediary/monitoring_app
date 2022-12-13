package ru.therapyapp.data_questionnaire.model

import ru.therapyapp.data_doctor.api.entity.Doctor

data class Questionnaire(
    val id: Int,
    val name: String,
    val doctor: Doctor,
    val forPatientId: Int?,
    val questions: List<Question>
)

data class QuestionnaireRequestBody(
    val name: String,
    val doctorId: Int,
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

enum class QuestionType(val questionTypeName: String) {
    FIELD("Поле ввода"),
    CHECKBOXES("Выбор из нескольких"),
    RADIOBUTTONS("Выбор из одного"),
}