package ru.therapyapp.data_auth.api.entity

data class User(
    val id: Int,
    val login: String,
    val userType: UserType
)

enum class UserType {
    DOCTOR, PATIENT
}