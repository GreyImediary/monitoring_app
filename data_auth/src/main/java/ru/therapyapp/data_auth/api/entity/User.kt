package ru.therapyapp.data_auth.api.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val login: String,
    val userType: UserType
) : Parcelable

enum class UserType {
    DOCTOR, PATIENT
}