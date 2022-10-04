package ru.therapyapp.feature_auth_impl.mvi

import ru.therapyapp.data_auth.api.entity.User

sealed class AuthSideEffect {
    data class OpenMainScreen(val user: User) : AuthSideEffect()
    data class OpenUserDataScreen(val user: User) : AuthSideEffect()
    object ShowRegisterView : AuthSideEffect()
    data class ShowMessage(val text: String) : AuthSideEffect()
}