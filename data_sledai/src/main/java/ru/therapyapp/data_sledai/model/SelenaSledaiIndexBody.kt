package ru.therapyapp.data_sledai.model

import java.util.Date

data class SelenaSledaiIndexBody(
    val patientId: Int,
    val answer: String,
    val date: Date,
    val sumValue: Int,
)