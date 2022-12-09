package ru.therapyapp.data_bvas.model

import java.util.Date

data class BvasRequestBody(
    val patientId: Int,
    val question1: List<BvasIndex.QuestionAnswer>,
    val question2: List<BvasIndex.QuestionAnswer>,
    val question3: List<BvasIndex.QuestionAnswer>,
    val question4: List<BvasIndex.QuestionAnswer>,
    val question5: List<BvasIndex.QuestionAnswer>,
    val question6: List<BvasIndex.QuestionAnswer>,
    val question7: List<BvasIndex.QuestionAnswer>,
    val question8: List<BvasIndex.QuestionAnswer>,
    val question9: List<BvasIndex.QuestionAnswer>,
    val date: Date,
    val sumValue: Int,
)