package ru.therapyapp.feature_user_data_impl.screen.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.data_auth.api.entity.UserType
import ru.therapyapp.feature_user_data_impl.screen.mvi.UserDataSideEffect
import ru.therapyapp.feature_user_data_impl.screen.mvi.UserDataViewModel

@Composable
fun UserDataScreen(
    viewModel: UserDataViewModel,
) {
    val state by viewModel.collectAsState()

    val activity = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = { handleSideEffect(activity, it) })

    val user = state.user
    user?.let {
        when (it.userType) {
            UserType.DOCTOR -> PatientDataView(userId = user.id, onEvent = { event -> viewModel.dispatch(event) })
            UserType.PATIENT -> DoctorDataView(userId = user.id, onEvent = { event -> viewModel.dispatch(event) })
        }
    }
}

private fun handleSideEffect(
    activity: AppCompatActivity,
    effect: UserDataSideEffect,
) {
    when (effect) {
        is UserDataSideEffect.OpenDoctorScreen -> {

        }
        is UserDataSideEffect.OpenPatientScreen -> {

        }
        is UserDataSideEffect.ShowToastMessage -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
    }
}