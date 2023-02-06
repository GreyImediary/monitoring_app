package ru.therapyapp.data_comments.model

import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_patient.api.entity.Patient
import java.util.Date

data class Comment(
    val patient: Patient,
    val doctor: Doctor,
    val comment: String,
    val date: Date
)