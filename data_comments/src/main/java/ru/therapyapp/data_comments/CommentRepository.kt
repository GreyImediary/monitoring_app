package ru.therapyapp.data_comments

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_comments.model.Comment

interface CommentRepository {
    suspend fun createComment(patientId: Int, comment: String): RequestResult<List<Comment>>

    suspend fun getCommentById(patientId: Int): RequestResult<List<Comment>>
}