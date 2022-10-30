package com.example.feature_asdas_impl

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.feature_asdas_impl.mvi.AsdasViewModel
import com.example.feature_asdas_impl.screen.AsdasScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AsdasActivity : AppCompatActivity() {
    private val viewModel: AsdasViewModel by viewModel {
        parametersOf(intent?.getIntExtra(PATIENT_ID, -1) ?: -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AsdasScreen(viewModel = viewModel)
        }
    }

    companion object {
        fun getIntent(activity: AppCompatActivity, patientId: Int): Intent {
            return Intent(activity, AsdasActivity::class.java).apply {
                putExtra(PATIENT_ID, patientId)
            }
        }

        private const val PATIENT_ID = "PATIENT_ID"
    }
}