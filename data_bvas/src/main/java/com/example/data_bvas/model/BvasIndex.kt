package com.example.data_bvas.model

import ru.therapyapp.data_patient.api.entity.Patient
import java.util.Date

data class BvasIndex(
    val patient: Patient,
    val question1: List<QuestionAnswer>,
    val question2: List<QuestionAnswer>,
    val question3: List<QuestionAnswer>,
    val question4: List<QuestionAnswer>,
    val question5: List<QuestionAnswer>,
    val question6: List<QuestionAnswer>,
    val question7: List<QuestionAnswer>,
    val question8: List<QuestionAnswer>,
    val question9: List<QuestionAnswer>,
    val date: Date,
    val sumValue: Int,
) {
    data class QuestionAnswer(
        val title: String,
        val value: Int,
    )
}