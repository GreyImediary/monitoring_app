package ru.therapyapp.feature_basdai_impl.mvi

data class BasdaiState(
    val patientId: Int = -1,
    val answerOne: Int = 0,
    val answerTwo: Int = 0,
    val answerThree: Int = 0,
    val answerFour: Int = 0,
    val answerFive: Int = 0,
    val answerSix: Int = 0,
    val sumValue: Double = 0.0
)

enum class QuestionNumber {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
}