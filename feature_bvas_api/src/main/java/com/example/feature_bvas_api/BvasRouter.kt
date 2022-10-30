package com.example.feature_bvas_api

import androidx.appcompat.app.AppCompatActivity

interface BvasRouter {
    fun openBvasScreen(activity: AppCompatActivity, patientId: Int)
}