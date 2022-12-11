package ru.therapyapp.feature_doctor_screen_impl.view.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire.model.Questionnaire
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnairesRoute(
    questionnaires: List<Questionnaire>,
    patients: List<Patient>,
    isRefreshing: Boolean,
    onBackClick: () -> Unit,
    onEvent: (DoctorScreenEvent) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Анкеты") },
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
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                items(questionnaires) { questionnaire ->
                    QuestionnaireCard(
                        questionnaire = questionnaire,
                        patients = patients, onClick = { /*TODO: show answered data*/ })
                }
            }
        }
    }
}

@Composable
private fun QuestionnaireCard(
    questionnaire: Questionnaire,
    patients: List<Patient>,
    onClick: () -> Unit,
) {
    val forPatient = if (questionnaire.forPatientId == null) {
        "для всех"
    } else {
        val patient = patients.find { it.id == questionnaire.forPatientId }

        "${patient?.surname} ${patient?.name} ${patient?.patronymic}"
    }
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.main_50))
            .clickable(
                indication = rememberRipple(),
                interactionSource = MutableInteractionSource(),
                onClick = { onClick() }
            )
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, top = 16.dp),
            text = questionnaire.name,
            fontWeight = FontWeight.Bold,
        )

        Divider(color = colorResource(id = R.color.main),
            modifier = Modifier.padding(bottom = 12.dp))

        Text(text = "Количество вопросов: ${questionnaire.questions.size}",
            modifier = Modifier.padding(start = 16.dp, top = 8.dp))
        Text(text = "Для пациента: $forPatient", modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp))
    }
}