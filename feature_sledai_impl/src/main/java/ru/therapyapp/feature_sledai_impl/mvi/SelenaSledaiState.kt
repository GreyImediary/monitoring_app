package ru.therapyapp.feature_sledai_impl.mvi

data class SelenaSledaiState(
    val patientId: Int,
    val answer: String = "",
    val sumValue: Int = 0
)