package ru.therapyapp.feature_doctor_screen_impl

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.therapyapp.data_doctor.api.entity.Doctor

class DoctorScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "DoctorScreen", modifier = Modifier.align(Alignment.Center))
            }
        }
    }

    companion object {
        private const val KEY_DOCTOR = "KEY_DOCTOR"

        fun getIntent(activity: AppCompatActivity, doctor: Doctor): Intent {
            return Intent(activity, DoctorScreenActivity::class.java).apply {
                putExtra(KEY_DOCTOR, doctor)
            }
        }
    }
}