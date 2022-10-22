package ru.therapyapp.data_basdai.model

import java.util.*

data class BasdaiIndexCreationBody(
    val patientId: Int,
    val question1Value: Int,
    val question2Value: Int,
    val question3Value: Int,
    val question4Value: Int,
    val question5Value: Int,
    val question6Value: Int,
    val date: Date,
    val sumValue: Double,
)