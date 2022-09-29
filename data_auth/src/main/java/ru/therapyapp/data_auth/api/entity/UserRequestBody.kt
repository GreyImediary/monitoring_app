package ru.therapyapp.data_auth.api.entity

data class UserRequestBody(
    val login: String,
    val password: String,
    val userType: UserType
)