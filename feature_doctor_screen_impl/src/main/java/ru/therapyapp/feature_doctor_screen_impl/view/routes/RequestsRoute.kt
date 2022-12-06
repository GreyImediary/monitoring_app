package ru.therapyapp.feature_doctor_screen_impl.view.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DoorBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_core.entity.Sex
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_request.api.entity.Request
import ru.therapyapp.data_request.api.entity.RequestCreationBody
import ru.therapyapp.data_request.api.entity.RequestStatus
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenEvent
import java.util.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RequestsRoute(
    doctorId: Int,
    requests: List<Request>,
    allPatients: List<Patient>,
    isRefreshing: Boolean,
    isCreateDialogOpened: Boolean,
    onEvent: (DoctorScreenEvent) -> Unit,
    onBackClick: () -> Unit,
) {

    var selectedPatient by rememberSaveable { mutableStateOf<Patient?>(null) }
    var patientText by rememberSaveable { mutableStateOf("") }
    var suggestions by rememberSaveable { mutableStateOf(listOf<Patient>()) }


    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text(text = "Заявки") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = { onEvent(DoctorScreenEvent.FetchData) }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                items(requests) { patient ->
                    RequestCard(
                        request = patient,
                        onEvent = onEvent,
                    )
                }
            }

            if (isCreateDialogOpened) {
                AlertDialog(
                    onDismissRequest = {
                        onEvent(DoctorScreenEvent.OnRequestDialogDismissClick)
                    },
                    title = {
                        Text(text = "Создать заявку")
                    },
                    text = {
                        Column {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = patientText,
                                onValueChange = {
                                    patientText = it
                                    suggestions = allPatients.filter { patient ->
                                        "${patient.surname} ${patient.name} ${patient.patronymic}"
                                            .contains(patientText)
                                    }

                                },
                                label = { Text("Пациент(ФИО)") },
                                trailingIcon = {
                                    IconButton(onClick = { patientText = "" }) {
                                        Icon(Icons.Outlined.Cancel, contentDescription = "")
                                    }
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(
                                    backgroundColor = colorResource(id = R.color.main_50),
                                    trailingIconColor = colorResource(id = R.color.icon_color),
                                    focusedLabelColor = colorResource(id = R.color.font_color),
                                    unfocusedLabelColor = colorResource(id = R.color.font_color),
                                    focusedTrailingIconColor = colorResource(id = R.color.icon_color),
                                    focusedIndicatorColor = colorResource(id = R.color.main),
                                    unfocusedIndicatorColor = colorResource(id = R.color.main)

                                ),
                            )
                            DropdownMenu(
                                expanded = suggestions.isNotEmpty(),
                                properties = PopupProperties(focusable = false),
                                onDismissRequest = { }
                            ) {
                                suggestions.forEach { patient ->
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedPatient = patient
                                            patientText =
                                                "${patient.surname} ${patient.name} ${patient.patronymic}"
                                        },
                                    ) {
                                        Text(text = "${patient.surname} ${patient.name} ${patient.patronymic}")
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        AppButton(
                            onClick = {
                                selectedPatient?.let {
                                    onEvent(DoctorScreenEvent.CreateRequest(
                                        requestCreationBody = RequestCreationBody(
                                            doctorId = doctorId,
                                            patientId = it.id,
                                            status = RequestStatus.PENDING,
                                        )
                                    ))
                                    onEvent(DoctorScreenEvent.OnRequestDialogDismissClick)
                                }
                            },
                        ) {
                            Text("Создать")
                        }
                    },
                    dismissButton = {
                        AppButton(
                            onClick = {
                                onEvent(DoctorScreenEvent.OnRequestDialogDismissClick)
                            },
                        ) {
                            Text("Отменить")
                        }
                    }
                )
            }
        }

    }
}

@Composable
fun RequestCard(
    request: Request,
    onEvent: (DoctorScreenEvent) -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }

    val status = when (request.status) {
        RequestStatus.ACCEPTED -> "Принята"
        RequestStatus.DECLINED -> "Отклонена"
        RequestStatus.PENDING -> "Ожидает подтверждения"
    }

    val doctor = request.doctor.run { "$surname $name $patronymic" }
    val patient = request.patient.run { "$surname $name $patronymic" }

    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.main_50))
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = "Статус: $status"
        )

        Divider(color = colorResource(id = R.color.main))

        Column(modifier = Modifier.padding(16.dp)) {
            Text(modifier = Modifier.padding(bottom = 8.dp), text = "Врач: $doctor")
            Text(text = "Пациент: $patient")
            if (request.status != RequestStatus.ACCEPTED && request.status != RequestStatus.DECLINED) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(top = 16.dp),
                        onClick = { openDialog = true },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = colorResource(id = R.color.icon_color)

                        )

                    ) {
                        Text(
                            text = "Удалить",
                        )
                    }
                }
            }
        }


        if (openDialog) {

            AlertDialog(
                onDismissRequest = {
                    openDialog = false
                },
                title = {
                    Text(text = "Отменить заявку?")
                },
                text = {
                    Text("Заявка будет удалена у Вас и у пациента")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onEvent(DoctorScreenEvent.DeleteRequest(request.id))
                            openDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.secondary),
                            contentColor = colorResource(id = R.color.color_white)
                        ),
                    ) {
                        Text("Да")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.secondary),
                            contentColor = colorResource(id = R.color.color_white)
                        ),
                    ) {
                        Text("Нет")
                    }
                }
            )
        }
    }


}

@Composable
@Preview(showSystemUi = true, device = Devices.TABLET)
private fun preview() {
    RequestsRoute(
        requests = listOf(
            Request(
                1,
                Doctor(
                    1,
                    2,
                    "Name",
                    "Surname",
                    "Patronymic",
                    Sex.MALE,
                    phoneNumber = "12839123",
                    null,
                    listOf(),
                ),
                Patient(
                    123,
                    45,
                    "Василий",
                    "Петрович",
                    "Иванович",
                    Sex.MALE,
                    "79250322314",
                    null,
                    null,
                    Date(),
                    patientCardNumber = "GV45431264"
                ),
                status = RequestStatus.ACCEPTED
            ),
            Request(
                1,
                Doctor(
                    1,
                    2,
                    "Name",
                    "Surname",
                    "Patronymic",
                    Sex.MALE,
                    phoneNumber = "12839123",
                    null,
                    listOf(),
                ),
                Patient(
                    123,
                    45,
                    "Василий",
                    "Петрович",
                    "Иванович",
                    Sex.MALE,
                    "79250322314",
                    null,
                    null,
                    Date(),
                    patientCardNumber = "GV45431264"
                ),
                status = RequestStatus.ACCEPTED
            ),
            Request(
                1,
                Doctor(
                    1,
                    2,
                    "Name",
                    "Surname",
                    "Patronymic",
                    Sex.MALE,
                    phoneNumber = "12839123",
                    null,
                    listOf(),
                ),
                Patient(
                    123,
                    45,
                    "Василий",
                    "Петрович",
                    "Иванович",
                    Sex.MALE,
                    "79250322314",
                    null,
                    null,
                    Date(),
                    patientCardNumber = "GV45431264"
                ),
                status = RequestStatus.PENDING
            )
        ),
        allPatients = listOf(),
        isRefreshing = false,
        onEvent = {},
        doctorId = -1,
        isCreateDialogOpened = false,
        onBackClick = {}
    )
}