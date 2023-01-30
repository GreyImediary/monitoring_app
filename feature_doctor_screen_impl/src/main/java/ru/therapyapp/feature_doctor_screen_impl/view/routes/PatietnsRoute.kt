package ru.therapyapp.feature_doctor_screen_impl.view.routes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R
import ru.therapyapp.core_ui.getCellCountForGrid
import ru.therapyapp.core_ui.getMediumHorizontalPadding
import ru.therapyapp.data_core.entity.Sex
import ru.therapyapp.data_core.utils.getStringDateRepresentation
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenEvent
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientRoute(
    patients: List<Patient>,
    isRefreshing: Boolean,
    onBackClick: () -> Unit,
    onEvent: (DoctorScreenEvent) -> Unit,
) {

    val localConfigWidth = LocalConfiguration.current.screenWidthDp
    val horizontalDp = getMediumHorizontalPadding(localConfigWidth.dp)
    val cellCount = getCellCountForGrid(localConfigWidth.dp)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Пациенты") },
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
            if (patients.isEmpty()) {
                Box(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .height(height = 1000.dp)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(cellCount),
                    contentPadding = PaddingValues(horizontal = horizontalDp, vertical = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    items(patients) { patient ->
                        PatientCard(
                            patient = patient,
                            onClick = {
                                onEvent(DoctorScreenEvent.OnPatientClick(patient))
                            },
                            onPatientScreenClick = {
                                onEvent(DoctorScreenEvent.OnPatientAppScreenClick(patient))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PatientCard(patient: Patient, onClick: () -> Unit, onPatientScreenClick: () -> Unit) {
    val name = if (!patient.patronymic.isNullOrBlank()) {
        "${patient.surname} ${patient.name.first()}. ${patient.patronymic?.first()}."
    } else {
        "${patient.surname} ${patient.name.first()}."
    }

    val sex = when (patient.sex) {
        Sex.MALE -> "Мужской"
        Sex.FEMALE -> "Женский"
    }

    Column(modifier = Modifier
        .background(color = colorResource(id = R.color.main_50))
        .clickable(
            indication = rememberRipple(),
            interactionSource = MutableInteractionSource(),
            onClick = { onClick() }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name)

            if (patient.patientCardNumber.isNotEmpty()) {
                Text(text = "№ ${patient.patientCardNumber}")
            }
        }
        Divider(
            color = colorResource(id = R.color.main)
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            text = "Пол: $sex",
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            text = "Дата рождения: ${patient.birthDate.getStringDateRepresentation()}",
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 16.dp),
            text = "Номер телефона: ${patient.phoneNumber}",
        )

        AppButton(
            modifier = Modifier.padding(top = 20.dp, start = 16.dp, bottom = 16.dp),
            onClick = {
                onPatientScreenClick()
            },
        ) {
            Text(text = "Индексы и анкеты", color = colorResource(id = R.color.color_white))
        }
    }


}

@Composable
@Preview(showSystemUi = true, device = Devices.TABLET)
private fun patientsPreview() {
    PatientRoute(
        patients = listOf(
            Patient(
                123,
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
            Patient(
                123,
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
            Patient(
                123,
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
            Patient(
                123,
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
        ),
        isRefreshing = false,
        onEvent = {},
        onBackClick = {}
    )
}