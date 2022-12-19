package ru.therapyapp.data_patient.api.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.therapyapp.data_core.entity.Sex
import java.util.*

@Parcelize
data class Patient(
    val id: Int,
    val name: String,
    val surname: String,
    val patronymic: String?,
    val sex: Sex,
    val phoneNumber: String,
    val additionalPhoneNumber: String?,
    val email: String?,
    val birthDate: Date,
    val patientCardNumber: String,
) : Parcelable