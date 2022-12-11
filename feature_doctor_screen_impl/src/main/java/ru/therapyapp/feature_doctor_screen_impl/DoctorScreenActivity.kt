package ru.therapyapp.feature_doctor_screen_impl

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenEvent
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenViewModel
import ru.therapyapp.feature_doctor_screen_impl.view.DoctorScreen

class DoctorScreenActivity : AppCompatActivity() {
    private val viewModel: DoctorScreenViewModel by viewModel {
        parametersOf(intent?.getParcelableExtra(KEY_DOCTOR))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DoctorScreen(viewModel = viewModel)
        }

        viewModel.dispatch(DoctorScreenEvent.FetchData)
    }

    override fun onResume() {
        super.onResume()
        viewModel.dispatch(DoctorScreenEvent.FetchData)
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