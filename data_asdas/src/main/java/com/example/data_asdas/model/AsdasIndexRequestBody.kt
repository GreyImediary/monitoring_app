package com.example.data_asdas.model

import java.util.Date

data class AsdasIndexRequestBody(
    val patientId: Int,
    val question1: Int,
    val question2: Int,
    val question3: Int,
    val question4: Int,
    val srbSoeType: SrbSoeType,
    val srbSoeValue: Double,
    val date: Date,
    val sumValue: Double
)