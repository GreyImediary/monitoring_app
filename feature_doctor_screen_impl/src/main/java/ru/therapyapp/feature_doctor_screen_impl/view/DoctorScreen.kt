package ru.therapyapp.feature_doctor_screen_impl.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.get
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.core_ui.R
import ru.therapyapp.feature_current_patient_api.CurrentPatientRouter
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenEvent
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenSideEffect
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenViewModel
import ru.therapyapp.feature_doctor_screen_impl.view.routes.PatientRoute
import ru.therapyapp.feature_doctor_screen_impl.view.routes.RequestsRoute

@Composable
fun DoctorScreen(
    viewModel: DoctorScreenViewModel,
    currentPatientRouter: CurrentPatientRouter = get(),
) {

    val state = viewModel.collectAsState().value
    val context = LocalContext.current as AppCompatActivity
    viewModel.collectSideEffect(sideEffect = {
        handleSideEffects(context, it, currentPatientRouter)
    })

    var selectedItem by remember { mutableStateOf(DoctorScreenViewRoute.PATIENTS) }
    val icons = listOf(Icons.Filled.Person, Icons.Filled.Description)
    val navController = rememberNavController()

    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        NavigationRail(
            backgroundColor = colorResource(id = R.color.main_50),
            elevation = 0.dp,
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(top = 100.dp, bottom = 40.dp),
                onClick = {
                    when (selectedItem) {
                        DoctorScreenViewRoute.PATIENTS -> {

                        }
                        DoctorScreenViewRoute.REQUESTS -> {
                            viewModel.dispatch(DoctorScreenEvent.OnRequestAddClick)
                        }
                    }
                },
                backgroundColor = colorResource(id = R.color.secondary),
                contentColor = colorResource(id = R.color.color_white),
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = null,
                )
            }

            DoctorScreenViewRoute.values().forEachIndexed { index, item ->
                val isSelected = selectedItem.ordinal == index
                val backColor = if (isSelected) {
                    colorResource(id = R.color.main)
                } else {
                    colorResource(id = R.color.main_50)
                }
                NavigationRailItem(
                    modifier = Modifier
                        .background(color = backColor),
                    selected = isSelected,
                    onClick = {
                        if (selectedItem != item) {
                            selectedItem = item
                            navController.navigate(item.title)
                        }
                    },
                    icon = { Icon(icons[index], contentDescription = item.title) },
                    label = { Text(text = item.title) },
                    selectedContentColor = colorResource(id = R.color.color_white),
                    unselectedContentColor = colorResource(id = R.color.icon_color)
                )
            }
        }
        NavHost(
            navController = navController,
            startDestination = DoctorScreenViewRoute.PATIENTS.title) {
            composable(DoctorScreenViewRoute.PATIENTS.title) {
                PatientRoute(
                    patients = state.doctor?.patients ?: listOf(),
                    onBackClick = {
                        navController.popBackStack(
                            DoctorScreenViewRoute.PATIENTS.title,
                            inclusive = true
                        )
                        selectedItem = DoctorScreenViewRoute.PATIENTS
                    },
                    onEvent = { viewModel.dispatch(it) },
                    isRefreshing = state.isRefreshing
                )
            }
            composable(DoctorScreenViewRoute.REQUESTS.title) {
                RequestsRoute(
                    doctorId = state.doctor?.id ?: -1,
                    requests = state.requests,
                    allPatients = state.allPatients,
                    onEvent = { viewModel.dispatch(it) },
                    onBackClick = {
                        navController.popBackStack(
                            DoctorScreenViewRoute.PATIENTS.title,
                            inclusive = true
                        )
                        selectedItem = DoctorScreenViewRoute.PATIENTS
                    },
                    isCreateDialogOpened = state.isRequestCreationDialogOpened,
                    isRefreshing = state.isRefreshing
                )
            }
        }
    }
}

private fun handleSideEffects(
    activity: AppCompatActivity,
    effect: DoctorScreenSideEffect,
    currentPatientRouter: CurrentPatientRouter,
) {
    when (effect) {
        is DoctorScreenSideEffect.OpenPatientDataScreen -> {
            currentPatientRouter.openCurrentPatientScreen(activity, effect.patient)
        }
        is DoctorScreenSideEffect.ShowToast -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
    }
}