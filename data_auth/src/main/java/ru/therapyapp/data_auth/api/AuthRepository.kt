package ru.therapyapp.data_auth.api

import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_auth.api.entity.UserRequestBody
import ru.therapyapp.data_core.entity.User

interface AuthRepository {
    suspend fun register(userRequestBody: UserRequestBody): RequestResult<User>
    suspend fun auth(login: String, password: String): RequestResult<User>
    suspend fun getUserById(usedId: Int): RequestResult<User>
}