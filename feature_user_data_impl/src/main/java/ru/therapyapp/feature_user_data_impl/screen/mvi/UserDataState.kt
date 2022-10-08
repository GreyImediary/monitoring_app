package ru.therapyapp.feature_user_data_impl.screen.mvi

import ru.therapyapp.data_auth.api.entity.User

data class UserDataState(
    val user: User? = null
)
