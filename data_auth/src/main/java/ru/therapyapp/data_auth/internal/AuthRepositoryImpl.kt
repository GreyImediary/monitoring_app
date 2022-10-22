package ru.therapyapp.data_auth.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.core_network.entity.getErrorMessage
import ru.therapyapp.data_auth.api.AuthRepository
import ru.therapyapp.data_auth.api.entity.UserRequestBody
import ru.therapyapp.data_core.entity.User

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

    override suspend fun getUserById(usedId: Int): RequestResult<User> {
        return withContext(dispatcher) {
            try {
                RequestResult.Success(service.getUserById(usedId))
            } catch (e: Exception) {
                RequestResult.Error(message = "Ошибка авторизации. Проверьте логин и пароль")
            }
        }
    }
}