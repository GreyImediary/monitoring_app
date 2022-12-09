package ru.therapyapp.feature_asdas_api

import androidx.appcompat.app.AppCompatActivity

interface AsdasRouter {
    fun openAsdasScreen(activity: AppCompatActivity, patientId: Int)
}