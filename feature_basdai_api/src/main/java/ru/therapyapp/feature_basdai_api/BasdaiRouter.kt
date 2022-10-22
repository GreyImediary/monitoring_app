package ru.therapyapp.feature_basdai_api

import androidx.appcompat.app.AppCompatActivity

interface BasdaiRouter {
    fun openBasdaiScreen(activity: AppCompatActivity, patientId: Int)
}