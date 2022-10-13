package ru.therapyapp.feature_auth_impl.mvi

import ru.therapyapp.data_core.entity.UserType

sealed class AuthEvent {
    object OnRegisterButtonClick : AuthEvent()
    data class OnLoginClick(val login: String, val password: String) : AuthEvent()
    data class OnRegister(val login: String, val password: String, val userType: UserType) : AuthEvent()
}