package ru.therapyapp.data_comments.model

import ru.therapyapp.data_patient.api.entity.Patient

data class Comment(
    val patient: Patient,
    val comment: String,
)