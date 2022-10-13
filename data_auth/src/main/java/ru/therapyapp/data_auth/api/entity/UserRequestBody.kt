package ru.therapyapp.data_auth.api.entity

import ru.therapyapp.data_core.entity.UserType

data class UserRequestBody(
    val login: String,
    val password: String,
    val userType: UserType
)