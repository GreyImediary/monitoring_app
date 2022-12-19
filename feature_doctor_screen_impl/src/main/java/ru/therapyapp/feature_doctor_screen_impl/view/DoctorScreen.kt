package ru.therapyapp.feature_doctor_screen_impl.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import ru.therapyapp.feature_answered_questionnaire_api.QuestionnaireAnsweredScreenRouter
import ru.therapyapp.feature_auth_api.AuthRouter
import ru.therapyapp.feature_current_patient_api.CurrentPatientRouter
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenEvent
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenSideEffect
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenViewModel
import ru.therapyapp.feature_doctor_screen_impl.view.routes.PatientRoute
import ru.therapyapp.feature_doctor_screen_impl.view.routes.QuestionnairesRoute
import ru.therapyapp.feature_doctor_screen_impl.view.routes.RequestsRoute
import ru.therapyapp.feature_patient_screen_api.PatientScreenRouter
import ru.therapyapp.feature_questionnaire_add_api.QuestionnaireAddScreenRouter
import ru.therapyapp.feature_user_data_api.UserDataRouter

@Composable
fun DoctorScreen(
    viewModel: DoctorScreenViewModel,
    currentPatientRouter: CurrentPatientRouter = get(),
    questionnaireAddScreenRouter: QuestionnaireAddScreenRouter = get(),
    questionnaireAnsweredScreenRouter: QuestionnaireAnsweredScreenRouter = get(),
    userDataRouter: UserDataRouter = get(),
    patientScreenRouter: PatientScreenRouter = get(),
    authRouter: AuthRouter = get(),
) {

    val state = viewModel.collectAsState().value
    val context = LocalContext.current as AppCompatActivity
    viewModel.collectSideEffect(sideEffect = {
        handleSideEffects(
            context,
            it,
            currentPatientRouter,
            questionnaireAddScreenRouter,
            questionnaireAnsweredScreenRouter,
            userDataRouter,
            patientScreenRouter,
            authRouter
        )
    })

    var selectedItem by remember { mutableStateOf(DoctorScreenViewRoute.PATIENTS) }
    val icons = listOf(Icons.Filled.Person, Icons.Filled.Description, Icons.Filled.Article)
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
                            viewModel.dispatch(DoctorScreenEvent.OpenPatientCreateScreen(state.doctor?.id ?: -1))
                        }
                        DoctorScreenViewRoute.REQUESTS -> {
                            viewModel.dispatch(DoctorScreenEvent.OnRequestAddClick)
                        }
                        DoctorScreenViewRoute.QUESTIONNAIRES -> {
                            viewModel.dispatch(DoctorScreenEvent.OnQuestionnaireAddClick)
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

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    modifier = Modifier.padding(top = 40.dp),
                    onClick = {
                        viewModel.dispatch(DoctorScreenEvent.Logout)
                    }) {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = null,
                        tint = colorResource(id = R.color.icon_color),
                    )
                }
                Text(text = "Выйти", color = colorResource(id = R.color.icon_color))
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

            composable(DoctorScreenViewRoute.QUESTIONNAIRES.title) {
                QuestionnairesRoute(
                    questionnaires = state.questionnaires,
                    patients = state.doctor?.patients ?: emptyList(),
                    isRefreshing = state.isRefreshing,
                    onBackClick = {
                        navController.popBackStack(
                            DoctorScreenViewRoute.PATIENTS.title,
                            inclusive = true
                        )
                        selectedItem = DoctorScreenViewRoute.PATIENTS
                    },
                    onEvent = { viewModel.dispatch(it) },
                )
            }
        }
    }
}

private fun handleSideEffects(
    activity: AppCompatActivity,
    effect: DoctorScreenSideEffect,
    currentPatientRouter: CurrentPatientRouter,
    questionnaireAddScreenRouter: QuestionnaireAddScreenRouter,
    questionnaireAnsweredScreenRouter: QuestionnaireAnsweredScreenRouter,
    userDataRouter: UserDataRouter,
    patientScreenRouter: PatientScreenRouter,
    authRouter: AuthRouter,
) {
    when (effect) {
        is DoctorScreenSideEffect.OpenPatientDataScreen -> {
            currentPatientRouter.openCurrentPatientScreen(activity, effect.patient)
        }
        is DoctorScreenSideEffect.ShowToast -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
        is DoctorScreenSideEffect.OpenQuestionnaireAddScreen -> {
            questionnaireAddScreenRouter.openQuestionnaireAddScreen(
                activity,
                effect.doctorId,
                effect.patients
            )
        }
        is DoctorScreenSideEffect.OpenAnsweredQuestionnaireScreen -> {
            questionnaireAnsweredScreenRouter.openQuestionnaireAnsweredScreen(
                activity,
                effect.questionnaireId
            )
        }
        DoctorScreenSideEffect.ShowAuthScreen -> {
            authRouter.showAuthScreen(activity)
        }
        is DoctorScreenSideEffect.OpenPatientCreateScreen -> {
            userDataRouter.openDataScreenForPatient(activity, effect.doctorId)
        }
        is DoctorScreenSideEffect.OpenPatientAppScreen -> {
            patientScreenRouter.openPatientScreen(activity, effect.patient)
        }
    }
}