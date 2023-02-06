package ru.therapyapp.feature_user_data_impl.screen.mvi

import ru.therapyapp.data_core.entity.User
import ru.therapyapp.data_mkb.model.Mkb

data class UserDataState(
    val user: User? = null,
    val doctorId: Int? = null,
    val mkbs: List<Mkb> = emptyList()
)
