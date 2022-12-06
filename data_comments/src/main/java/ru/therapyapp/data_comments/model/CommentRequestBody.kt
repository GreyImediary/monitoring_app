package ru.therapyapp.data_comments.model

data class CommentRequestBody(
    val patientId: Int,
    val comment: String,
)