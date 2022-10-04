package ru.therapyapp.data_patient.api.entity

import ru.therapyapp.data_core.entity.Sex
import java.util.*

data class Patient(
    val id: Int,
    val userId: Int,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val sex: Sex,
    val phoneNumber: String,
    val additionalPhoneNumber: String?,
    val email: String?,
    val birthDate: Date,
    val patientCardNumber: String,
)