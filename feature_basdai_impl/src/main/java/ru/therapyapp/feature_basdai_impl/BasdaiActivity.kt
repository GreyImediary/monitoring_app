package ru.therapyapp.feature_basdai_impl

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.therapyapp.feature_basdai_impl.mvi.BasdaiViewModel
import ru.therapyapp.feature_basdai_impl.screen.BasdaiScreen

class BasdaiActivity : AppCompatActivity() {
    private val viewModel: BasdaiViewModel by viewModel {
        parametersOf(intent?.getIntExtra(PATIENT_ID, -1) ?: -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BasdaiScreen(viewModel = viewModel)
        }
    }

    companion object {
        fun getIntent(activity: AppCompatActivity, patientId: Int): Intent {
            return Intent(activity, BasdaiActivity::class.java).apply {
                putExtra(PATIENT_ID, patientId)
            }
        }

        private const val PATIENT_ID = "PATIENT_ID"
    }
}