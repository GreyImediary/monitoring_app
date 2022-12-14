package ru.therapyapp.feature_patient_screen_impl.view.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_request.api.entity.Request
import ru.therapyapp.data_request.api.entity.RequestStatus
import ru.therapyapp.data_request.api.entity.RequestUpdateBody
import ru.therapyapp.feature_patient_screen_impl.mvi.PatientScreenEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestRoute(
    isRefreshing: Boolean,
    requests: List<Request>,
    onMenuClick: () -> Unit,
    onEvent: (PatientScreenEvent) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Заявки") },
                navigationIcon = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )
        }
    ) {
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = { onEvent(PatientScreenEvent.FetchData) }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(requests) { patient ->
                    RequestCard(
                        request = patient,
                        onEvent = onEvent,
                    )
                }
            }
        }
    }
}

@Composable
private fun RequestCard(
    request: Request,
    onEvent: (PatientScreenEvent) -> Unit,
) {
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
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = "Статус: $status"
            )
        }

        Divider(color = colorResource(id = R.color.main))

        Column(modifier = Modifier.padding(16.dp)) {
            Text(modifier = Modifier.padding(bottom = 8.dp), text = "Врач: $doctor")
            Text(text = "Пациент: $patient")


            if (request.status != RequestStatus.ACCEPTED && request.status != RequestStatus.DECLINED) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            onEvent(
                                PatientScreenEvent.OnUpdateRequest(
                                    RequestUpdateBody(
                                        request.id,
                                        RequestStatus.DECLINED,
                                    ),
                                )
                            )
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = colorResource(id = R.color.icon_color)

                        )
                    ) {
                        Text("Отклонить")
                    }

                    TextButton(
                        onClick = {
                            onEvent(
                                PatientScreenEvent.OnUpdateRequest(
                                    RequestUpdateBody(
                                        request.id,
                                        RequestStatus.ACCEPTED,
                                    ),
                                )
                            )
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = colorResource(id = R.color.icon_color)

                        )
                    ) {
                        Text("Принять")
                    }
                }
            }
        }
    }
}