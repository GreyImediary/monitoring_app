package ru.therapyapp.feature_doctor_screen_impl

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.feature_doctor_screen_api.DoctorScreenRouter

class DoctorScreenRouterImpl : DoctorScreenRouter {
    override fun openDoctorScreen(activity: AppCompatActivity, doctor: Doctor) {
        activity.startActivity(DoctorScreenActivity.getIntent(activity, doctor))
    }
}