package ru.therapyapp.feature_patient_screen_impl

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.therapyapp.data_patient.api.entity.Patient

class PatientScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "PatientScreen", modifier = Modifier.align(Alignment.Center))
            }
        }
    }

    companion object {
        private const val KEY_PATIENT = "KEY_PATIENT"

        fun getIntent(activity: AppCompatActivity, patient: Patient): Intent {
            return Intent(activity, PatientScreenActivity::class.java).apply {
                putExtra(KEY_PATIENT, patient)
            }
        }
    }
}