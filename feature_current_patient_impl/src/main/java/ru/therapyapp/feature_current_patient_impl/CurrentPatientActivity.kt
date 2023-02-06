package ru.therapyapp.feature_current_patient_impl

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_current_patient_impl.mvi.CurrentPatientEvent
import ru.therapyapp.feature_current_patient_impl.mvi.CurrentPatientViewModel
import ru.therapyapp.feature_current_patient_impl.view.PatientScreen

class CurrentPatientActivity : AppCompatActivity() {
    private val viewModel: CurrentPatientViewModel by viewModel {
        parametersOf(
            intent?.getParcelableExtra(KEY_PATIENT),
            intent?.getIntExtra(KEY_DOCTOR_ID, -1) ?: -1
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatientScreen(viewModel, onEvent = { viewModel.dispatch(it) })
        }

        viewModel.dispatch(CurrentPatientEvent.FetchData)
    }

    companion object {
        private const val KEY_PATIENT = "KEY_PATIENT"
        private const val KEY_DOCTOR_ID = "KEY_DOCTOR_ID"

        fun getIntent(
            activity: AppCompatActivity,
            patient: Patient,
            doctorId: Int
        ): Intent {
            return Intent(activity, CurrentPatientActivity::class.java).apply {
                putExtra(KEY_PATIENT, patient)
                putExtra(KEY_DOCTOR_ID, doctorId)
            }
        }
    }
}