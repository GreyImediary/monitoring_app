package ru.therapyapp.data_auth.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_auth.api.AuthRepository
import ru.therapyapp.core_network.entity.User
import ru.therapyapp.data_auth.api.entity.UserRequestBody

internal class AuthRepositoryImpl(
    private val service: AuthService,
    private val dispatcher: CoroutineDispatcher,
) : AuthRepository {

    override suspend fun register(userRequestBody: UserRequestBody): RequestResult<User> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(service.register(userRequestBody))
            } catch (e: Exception) {
                RequestResult.Error(e.getErrorMessage())
            }
        }
    }

    override suspend fun auth(login: String, password: String): RequestResult<User> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(service.auth(login, password))
            } catch (e: Exception) {
                RequestResult.Error(message = "Ошибка авторизации. Проверьте логин и пароль")
            }
        }
    }
}