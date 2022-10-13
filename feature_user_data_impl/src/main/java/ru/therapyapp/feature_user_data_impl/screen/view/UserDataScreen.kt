package ru.therapyapp.feature_user_data_impl.screen.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import org.koin.androidx.compose.get
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.data_core.entity.UserType
import ru.therapyapp.feature_doctor_screen_api.DoctorScreenRouter
import ru.therapyapp.feature_patient_screen_api.PatientScreenRouter
import ru.therapyapp.feature_user_data_impl.screen.mvi.UserDataSideEffect
import ru.therapyapp.feature_user_data_impl.screen.mvi.UserDataViewModel

@Composable
fun UserDataScreen(
    viewModel: UserDataViewModel,
    doctorScreenRouter: DoctorScreenRouter = get(),
    patientScreenRouter: PatientScreenRouter = get(),
) {
    val state by viewModel.collectAsState()

    val activity = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = {
        handleSideEffect(activity, it, doctorScreenRouter, patientScreenRouter)
    })

    val user = state.user
    user?.let {
        when (it.userType) {
            UserType.DOCTOR -> DoctorDataView(userId = user.id,
                onEvent = { event -> viewModel.dispatch(event) })
            UserType.PATIENT -> PatientDataView(userId = user.id,
                onEvent = { event -> viewModel.dispatch(event) })
        }
    }
}

private fun handleSideEffect(
    activity: AppCompatActivity,
    effect: UserDataSideEffect,
    doctorScreenRouter: DoctorScreenRouter,
    patientScreenRouter: PatientScreenRouter,
) {
    when (effect) {
        is UserDataSideEffect.OpenDoctorScreen -> {
            doctorScreenRouter.openDoctorScreen(activity, effect.doctor)
        }
        is UserDataSideEffect.OpenPatientScreen -> {
            patientScreenRouter.openPatientScreen(activity, effect.patient)
        }
        is UserDataSideEffect.ShowToastMessage -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
    }
}