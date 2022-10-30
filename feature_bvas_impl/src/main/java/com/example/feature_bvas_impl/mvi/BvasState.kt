package com.example.feature_bvas_impl.mvi

import com.example.data_bvas.model.BvasIndex

data class BvasState(
    val patientId: Int,
    val question1: List<BvasIndex.QuestionAnswer> = listOf(),
    val question2: List<BvasIndex.QuestionAnswer> = listOf(),
    val question3: List<BvasIndex.QuestionAnswer> = listOf(),
    val question4: List<BvasIndex.QuestionAnswer> = listOf(),
    val question5: List<BvasIndex.QuestionAnswer> = listOf(),
    val question6: List<BvasIndex.QuestionAnswer> = listOf(),
    val question7: List<BvasIndex.QuestionAnswer> = listOf(),
    val question8: List<BvasIndex.QuestionAnswer> = listOf(),
    val question9: List<BvasIndex.QuestionAnswer> = listOf(),
    val sumValue: Int = 0,
)

enum class QuestionNumber {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
}