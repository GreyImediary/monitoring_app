package ru.therapyapp.data_comments.internal

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_comments.CommentRepository
import ru.therapyapp.data_comments.model.Comment
import ru.therapyapp.data_comments.model.CommentRequestBody

internal class CommentRepositoryImpl(
    private val commentService: CommentService,
    private val dispatcher: CoroutineDispatcher
) : CommentRepository {
    override suspend fun createComment(patientId: Int, comment: String): RequestResult<List<Comment>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(commentService.sendComment(CommentRequestBody(patientId, comment)))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun getCommentById(patientId: Int): RequestResult<List<Comment>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(commentService.getCommentsByPatient(patientId))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }
}