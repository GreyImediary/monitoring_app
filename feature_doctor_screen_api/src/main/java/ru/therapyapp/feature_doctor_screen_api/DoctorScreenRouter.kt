package ru.therapyapp.feature_doctor_screen_api

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_doctor.api.entity.Doctor

interface DoctorScreenRouter {
    fun openDoctorScreen(activity: AppCompatActivity, doctor: Doctor)
}