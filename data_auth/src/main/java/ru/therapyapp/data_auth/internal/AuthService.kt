package ru.therapyapp.data_auth.internal

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import ru.therapyapp.data_auth.api.entity.UserRequestBody
import ru.therapyapp.data_core.entity.User

internal interface AuthService {

    @FormUrlEncoded
    @POST("api/v1/auth")
    suspend fun auth(@Field("login") login: String, @Field("password") password: String): User

    @POST("api/v1/register")
    suspend fun register(@Body userRequestBody: UserRequestBody): User
}