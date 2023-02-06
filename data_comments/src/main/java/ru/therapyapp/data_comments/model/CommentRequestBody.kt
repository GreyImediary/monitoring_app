package ru.therapyapp.data_comments.model

import java.util.Date

data class CommentRequestBody(
    val patientId: Int,
    val doctorId: Int,
    val comment: String,
    val date: Date
)