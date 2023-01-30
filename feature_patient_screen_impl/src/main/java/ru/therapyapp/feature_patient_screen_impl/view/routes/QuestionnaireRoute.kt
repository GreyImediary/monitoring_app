package ru.therapyapp.feature_patient_screen_impl.view.routes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_questionnaire.model.Questionnaire
import ru.therapyapp.feature_patient_screen_impl.mvi.PatientScreenEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnaireRoute(
    isRefreshing: Boolean,
    questionnaires: List<Questionnaire>,
    onMenuClick: () -> Unit,
    onEvent: (PatientScreenEvent) -> Unit,
) {
    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { onEvent(PatientScreenEvent.FetchData) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Анкеты") },
                    navigationIcon = {
                        IconButton(onClick = { onMenuClick() }) {
                            Icon(Icons.Filled.Menu, contentDescription = null)
                        }
                    }
                )
            }
        )
        {
            if (questionnaires.isEmpty()) {
                Box(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .height(height = 1000.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(questionnaires) { questionnaire ->
                        QuestionnaireCard(
                            questionnaire = questionnaire,
                            onClick = { onEvent(PatientScreenEvent.OnQuestionnaireClick(questionnaire.id)) }
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun QuestionnaireCard(
    questionnaire: Questionnaire,
    onClick: () -> Unit,
) {
    val doctor = questionnaire.doctor
    val fromDoctor = if (doctor.patronymic != null) {
        "${doctor.surname} ${doctor.name} ${doctor.patronymic}"
    } else {
        "${doctor.surname} ${doctor.name}"
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
            modifier = Modifier.padding(bottom = 12.dp, start = 12.dp, top = 12.dp),
            text = questionnaire.name,
            fontWeight = FontWeight.Bold,
        )

        Divider(color = colorResource(id = R.color.main),
            modifier = Modifier.padding(bottom = 12.dp))

        Text(text = "Количество вопросов: ${questionnaire.questions.size}",
            modifier = Modifier.padding(start = 12.dp, top = 8.dp))
        Text(text = "От врача: $fromDoctor",
            modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 12.dp))
    }
}