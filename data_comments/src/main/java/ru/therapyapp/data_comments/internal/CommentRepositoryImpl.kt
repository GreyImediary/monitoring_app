package ru.therapyapp.data_comments.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_comments.CommentRepository
import ru.therapyapp.data_comments.model.Comment
import ru.therapyapp.data_comments.model.CommentRequestBody
import java.util.*

internal class CommentRepositoryImpl(
    private val commentService: CommentService,
    private val dispatcher: CoroutineDispatcher
) : CommentRepository {
    override suspend fun createComment(patientId: Int, doctorId: Int, comment: String): RequestResult<List<Comment>> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(
                    commentService.sendComment(
                        CommentRequestBody(patientId, doctorId, comment, Date()),
                    )
                )
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