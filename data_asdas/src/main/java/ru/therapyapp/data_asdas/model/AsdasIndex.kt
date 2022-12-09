package ru.therapyapp.data_asdas.model

import ru.therapyapp.data_patient.api.entity.Patient
import java.util.Date

data class AsdasIndex(
    val patient: Patient,
    val question1: Int,
    val question2: Int,
    val question3: Int,
    val question4: Int,
    val srbSoeType: SrbSoeType,
    val srbSoeValue: Double,
    val date: Date,
    val sumValue: Double
)