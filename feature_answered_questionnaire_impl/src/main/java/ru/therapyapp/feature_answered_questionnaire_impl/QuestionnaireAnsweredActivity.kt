package ru.therapyapp.feature_answered_questionnaire_impl

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnswered
import ru.therapyapp.feature_answered_questionnaire_impl.mvi.QuestionnaireAnsweredEvent
import ru.therapyapp.feature_answered_questionnaire_impl.mvi.QuestionnaireAnsweredViewModel
import ru.therapyapp.feature_answered_questionnaire_impl.view.QuestionnaireAnsweredScreen

class QuestionnaireAnsweredActivity : AppCompatActivity() {
    private val viewModel: QuestionnaireAnsweredViewModel by viewModel {
        parametersOf(
            intent?.getIntExtra(KEY_QUESTIONNAIRE_ID, -1) ?: -1,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            QuestionnaireAnsweredScreen(viewModel = viewModel)
        }

        viewModel.dispatch(QuestionnaireAnsweredEvent.FetchData)
    }

    companion object {
        private const val KEY_QUESTIONNAIRE_ID = "KEY_QUESTIONNAIRE_ID"

        fun getIntent(activity: AppCompatActivity, questionnaireId: Int): Intent {
            return Intent(activity, QuestionnaireAnsweredActivity::class.java).apply {
                putExtra(KEY_QUESTIONNAIRE_ID, questionnaireId)
            }
        }
    }
}