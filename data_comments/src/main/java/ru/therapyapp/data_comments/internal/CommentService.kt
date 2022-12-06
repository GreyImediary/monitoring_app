package ru.therapyapp.data_comments.internal

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.therapyapp.data_comments.model.Comment
import ru.therapyapp.data_comments.model.CommentRequestBody

internal interface CommentService {

    @POST("api/v1/comment")
    suspend fun sendComment(@Body commentRequestBody: CommentRequestBody): List<Comment>

    @GET("api/v1/comment/byPatient/{id}")
    suspend fun getCommentsByPatient(@Path("id") patientId: Int): List<Comment>
}