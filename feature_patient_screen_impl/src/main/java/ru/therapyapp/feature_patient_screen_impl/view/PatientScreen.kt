package ru.therapyapp.feature_patient_screen_impl.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.therapyapp.feature_asdas_api.AsdasRouter
import ru.therapyapp.feature_bvas_api.BvasRouter
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_core.utils.getStringDateRepresentation
import ru.therapyapp.feature_basdai_api.BasdaiRouter
import ru.therapyapp.feature_patient_screen_impl.mvi.PatientScreenSideEffect
import ru.therapyapp.feature_patient_screen_impl.mvi.PatientScreenViewModel
import ru.therapyapp.feature_patient_screen_impl.view.routes.IndexesRoute
import ru.therapyapp.feature_patient_screen_impl.view.routes.RequestRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientScreen(
    viewModel: PatientScreenViewModel,
    basdaiRouter: BasdaiRouter = get(),
    asdasRouter: AsdasRouter = get(),
    bvasRouter: BvasRouter = get(),
) {
    val context = LocalContext.current as AppCompatActivity
    val state = viewModel.collectAsState().value
    viewModel.collectSideEffect(sideEffect = {
        handleSideEffects(
            context,
            state.patient?.id ?: -1,
            basdaiRouter,
            asdasRouter,
            bvasRouter,
            it
        )
    })

    val patient = state.patient
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItem by rememberSaveable { mutableStateOf(PatientScreenViewRoute.INDEXES) }
    val icons = listOf(Icons.Filled.BarChart, Icons.Filled.Description, Icons.Filled.Person)
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = colorResource(id = R.color.main)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 12.dp)
                        .background(
                            color = colorResource(id = R.color.icon_color),
                            RoundedCornerShape(8.dp)
                        ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${patient?.surname} ${patient?.name}",
                            color = colorResource(id = R.color.color_white))
                        IconButton(onClick = { /*TODO: logout*/ }) {
                            Icon(
                                imageVector = Icons.Filled.ExitToApp,
                                contentDescription = null,
                                tint = colorResource(id = R.color.color_white),
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                        text = "Пол: ${patient?.sex?.sex}",
                        color = colorResource(id = R.color.color_white)
                    )

                    Text(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                        text = "Телефон: ${patient?.phoneNumber}",
                        color = colorResource(id = R.color.color_white)
                    )

                    Text(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        text = "Дата рождения: ${patient?.birthDate?.getStringDateRepresentation()}",
                        color = colorResource(id = R.color.color_white)
                    )

                    Spacer(
                        modifier = Modifier.padding(top = 30.dp),
                    )
                }
                Spacer(Modifier.height(12.dp))
                PatientScreenViewRoute.values().forEachIndexed { index, patientScreenViewRoute ->
                    val selected = selectedItem == patientScreenViewRoute
                    NavigationDrawerItem(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        label = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.padding(end = 8.dp),
                                    imageVector = icons[index],
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.color_white)
                                )
                                Text(
                                    text = patientScreenViewRoute.title,
                                    color = colorResource(id = R.color.color_white)
                                )
                            }
                        },
                        selected = selected,
                        onClick = {
                            selectedItem = patientScreenViewRoute
                            navController.navigate(patientScreenViewRoute.title)
                            scope.launch { drawerState.close() }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = colorResource(id = R.color.main_dark),
                            unselectedContainerColor = colorResource(id = R.color.main)
                        )
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController,
            startDestination = PatientScreenViewRoute.INDEXES.title) {
            composable(PatientScreenViewRoute.INDEXES.title) {
                IndexesRoute(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onEvent = {
                        viewModel.dispatch(it)
                    },
                )
            }
            composable(PatientScreenViewRoute.REQUESTS.title) {
                RequestRoute(
                    isRefreshing = state.isRefreshing,
                    requests = state.requests,
                    onEvent = { viewModel.dispatch(it) },
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
            composable(PatientScreenViewRoute.DOCTORS.title) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "врачи")
                }
            }
        }
    }
}

private fun handleSideEffects(
    activity: AppCompatActivity,
    patientId: Int,
    basdaiRouter: BasdaiRouter,
    asdasRouter: AsdasRouter,
    bvasRouter: BvasRouter,
    effect: PatientScreenSideEffect,
) {
    when (effect) {
        is PatientScreenSideEffect.ShowToast -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
        PatientScreenSideEffect.OpenBasdaiScreen -> {
            basdaiRouter.openBasdaiScreen(activity, patientId)
        }
        PatientScreenSideEffect.OpenAsdasScreen -> {
            asdasRouter.openAsdasScreen(activity, patientId)
        }
        PatientScreenSideEffect.OpenBvasScreen -> {
            bvasRouter.openBvasScreen(activity, patientId)
        }
    }
}