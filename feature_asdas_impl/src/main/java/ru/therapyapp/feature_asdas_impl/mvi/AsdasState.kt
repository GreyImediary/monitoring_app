package ru.therapyapp.feature_asdas_impl.mvi

import ru.therapyapp.data_asdas.model.SrbSoeType

data class AsdasState(
    val patientId: Int = 0,
    val question1: Int = 0,
    val question2: Int = 0,
    val question3: Int = 0,
    val question4: Int = 0,
    val srbSoeType: SrbSoeType = SrbSoeType.SRB,
    val srbSoeValue: Double = 0.0,
    val sumValue: Double = 0.64
)


enum class QuestionNumber {
    ONE,
    TWO,
    THREE,
    FOUR,
}