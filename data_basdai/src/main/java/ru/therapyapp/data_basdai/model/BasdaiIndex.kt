package ru.therapyapp.data_basdai.model

import ru.therapyapp.data_patient.api.entity.Patient
import java.util.Date

data class BasdaiIndex(
    val patient: Patient,
    val question1Value: Int,
    val question2Value: Int,
    val question3Value: Int,
    val question4Value: Int,
    val question5Value: Int,
    val question6Value: Int,
    val date: Date,
    val sumValue: Double,
)