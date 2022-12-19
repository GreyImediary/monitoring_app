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
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.syntax.simple.intent
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_patient_screen_impl.mvi.PatientScreenEvent
import ru.therapyapp.feature_patient_screen_impl.mvi.PatientScreenViewModel
import ru.therapyapp.feature_patient_screen_impl.view.PatientScreen

class PatientScreenActivity : AppCompatActivity() {

    private val viewModel: PatientScreenViewModel by viewModel {
        parametersOf(intent?.getParcelableExtra(KEY_PATIENT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PatientScreen(viewModel = viewModel)
        }

        viewModel.dispatch(PatientScreenEvent.FetchData)
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