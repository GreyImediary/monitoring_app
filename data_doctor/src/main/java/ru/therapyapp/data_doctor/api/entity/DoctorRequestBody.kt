package ru.therapyapp.data_doctor.api.entity

import ru.therapyapp.data_core.entity.Sex

data class DoctorRequestBody(
    val userId: Int,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val sex: Sex,
    val phoneNumber: String,
    val email: String?,
)