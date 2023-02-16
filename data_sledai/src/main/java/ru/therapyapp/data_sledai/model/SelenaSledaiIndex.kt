package ru.therapyapp.data_sledai.model

import ru.therapyapp.data_patient.api.entity.Patient
import java.util.Date

data class SelenaSledaiIndex(
    val patient: Patient,
    val answer: String,
    val date: Date,
    val sumValue: Int,
)

