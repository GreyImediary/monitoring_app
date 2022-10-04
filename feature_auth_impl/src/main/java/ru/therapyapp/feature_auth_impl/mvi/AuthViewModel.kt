package ru.therapyapp.feature_auth_impl.mvi

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.core_network.entity.RequestResult
import ru.therapyapp.data_auth.api.AuthRepository
import ru.therapyapp.data_auth.api.entity.UserRequestBody
import ru.therapyapp.data_auth.api.entity.UserType

class AuthViewModel(
    private val authRepository: AuthRepository
) : MviViewModel<AuthEvent, AuthState, AuthSideEffect>(initialState = AuthState()) {

    override fun dispatch(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnLoginClick -> onLogin(event.login, event.password)
            AuthEvent.OnRegisterButtonClick -> onRegisterButtonClick()
            is AuthEvent.OnRegister -> onRegister(event.login, event.password, event.userType)
        }
    }

    private fun onLogin(login: String, password: String) {
        intent {
            when (val loginResult = authRepository.auth(login, PasswordMapper.md5(password))) {
                is RequestResult.Success -> {
                    postSideEffect(AuthSideEffect.OpenMainScreen(loginResult.data))
                }
                is RequestResult.Error -> {
                    postSideEffect(AuthSideEffect.ShowMessage(loginResult.message ?: ""))
                }
            }
        }
    }

    private fun onRegister(login: String, password: String, userType: UserType) {
        intent {
            val userResult = authRepository.register(
                UserRequestBody(login, PasswordMapper.md5(password), userType)
            )

            when (userResult) {
                is RequestResult.Error -> {
                    postSideEffect(AuthSideEffect.ShowMessage(userResult.message ?: ""))
                }
                is RequestResult.Success -> {
                    postSideEffect(AuthSideEffect.OpenUserDataScreen(userResult.data))
                }
            }

        }
    }

    private fun onRegisterButtonClick() {
        intent {
            postSideEffect(AuthSideEffect.ShowRegisterView)
        }
    }
}