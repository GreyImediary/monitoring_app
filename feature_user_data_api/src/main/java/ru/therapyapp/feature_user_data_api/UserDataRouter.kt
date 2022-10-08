package ru.therapyapp.feature_user_data_api

import android.service.autofill.UserData
import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_auth.api.entity.User

interface UserDataRouter {
    fun openUserDataScreen(activity: AppCompatActivity, user: User)
}