package ru.therapyapp.feature_questionnaire_add_impl

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_questionnaire_add_impl.mvi.QuestionnaireAddViewModel
import ru.therapyapp.feature_questionnaire_add_impl.view.QuestionnaireAddScreen

class QuestionnaireAddActivity : AppCompatActivity() {
    private val viewModel: QuestionnaireAddViewModel by viewModel {
        parametersOf(
            intent?.getIntExtra(KEY_DOCTOR_ID, -1) ?: -1,
            (intent?.getSerializableExtra(KEY_PATIENTS) as? ArrayList<Patient>)?.toList() ?: emptyList<Patient>()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            QuestionnaireAddScreen(viewModel = viewModel)
        }
    }

    companion object {
        private const val KEY_DOCTOR_ID = "KEY_DOCTOR_ID"
        private const val KEY_PATIENTS = "KEY_PATIENTS"

        fun getIntent(activity: AppCompatActivity, doctorId: Int, patients: List<Patient>): Intent {
            return Intent(activity, QuestionnaireAddActivity::class.java).apply {
                putExtra(KEY_DOCTOR_ID, doctorId)
                putExtra(KEY_PATIENTS, patients as ArrayList<Patient>)
            }
        }
    }
}