package ru.therapyapp.feature_bvas_impl

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.feature_bvas_impl.mvi.BvasViewModel
import ru.therapyapp.feature_bvas_impl.screen.BvasScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BvasActivity : AppCompatActivity() {
    private val viewModel: BvasViewModel by viewModel {
        parametersOf(intent?.getIntExtra(PATIENT_ID, -1) ?: -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BvasScreen(viewModel = viewModel)
        }
    }

    companion object {
        fun getIntent(activity: AppCompatActivity, patientId: Int): Intent {
            return Intent(activity, BvasActivity::class.java).apply {
                putExtra(PATIENT_ID, patientId)
            }
        }

        private const val PATIENT_ID = "PATIENT_ID"
    }
}