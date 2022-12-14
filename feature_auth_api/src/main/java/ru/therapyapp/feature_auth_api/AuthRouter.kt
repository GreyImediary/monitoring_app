package ru.therapyapp.feature_auth_api

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

interface AuthRouter {
    fun showAuthScreen(activity: AppCompatActivity)
}