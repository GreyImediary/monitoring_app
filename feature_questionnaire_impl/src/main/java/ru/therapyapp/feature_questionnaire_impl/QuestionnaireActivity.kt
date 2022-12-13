package ru.therapyapp.feature_questionnaire_impl

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.therapyapp.feature_questionnaire_impl.mvi.QuestionnaireEvent
import ru.therapyapp.feature_questionnaire_impl.mvi.QuestionnaireViewModel
import ru.therapyapp.feature_questionnaire_impl.view.QuestionnaireScreen

class QuestionnaireActivity : AppCompatActivity() {
    private val viewModel: QuestionnaireViewModel by viewModel {
        parametersOf(
            intent?.getIntExtra(KEY_PATIENT_ID, -1) ?: -1,
            intent?.getIntExtra(KEY_QUESTIONNAIRE_ID, -1) ?: -1,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            QuestionnaireScreen(viewModel = viewModel)
        }

        viewModel.dispatch(QuestionnaireEvent.FetchData)
    }

    companion object {
        private const val KEY_PATIENT_ID = "KEY_PATIENT_ID"
        private const val KEY_QUESTIONNAIRE_ID = "KEY_QUESTIONNAIRE_ID"

        fun getIntent(activity: AppCompatActivity, patientId: Int, questionnaireId: Int): Intent {
            return Intent(activity, QuestionnaireActivity::class.java).apply {
                putExtra(KEY_PATIENT_ID, patientId)
                putExtra(KEY_QUESTIONNAIRE_ID, questionnaireId)
            }
        }
    }
}