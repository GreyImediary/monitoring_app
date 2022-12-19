package ru.therapyapp.feature_user_data_api

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_core.entity.User

interface UserDataRouter {
    fun openUserDataScreen(activity: AppCompatActivity, user: User)
    fun openDataScreenForPatient(activity: AppCompatActivity, doctorId: Int)
}