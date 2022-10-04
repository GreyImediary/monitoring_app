package ru.therapyapp.data_auth.api

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.User
import ru.therapyapp.data_auth.api.entity.UserRequestBody

interface AuthRepository {
    suspend fun register(userRequestBody: UserRequestBody): RequestResult<User>
    suspend fun auth(login: String, password: String): RequestResult<User>
}