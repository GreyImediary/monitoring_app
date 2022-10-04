package ru.therapyapp.data_doctor.api.entity

import ru.therapyapp.data_core.entity.Sex
import ru.therapyapp.data_patient.api.entity.Patient

data class Doctor(
    val id: Int,
    val userId: Int,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val sex: Sex,
    val phoneNumber: String,
    val email: String?,
    val patients: List<Patient>
)