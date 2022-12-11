package ru.therapyapp.feature_questionnaire_add_impl.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.data_questionnaire.model.Questionnaire
import ru.therapyapp.feature_questionnaire_add_impl.mvi.QuestionnaireAddEvent
import ru.therapyapp.feature_questionnaire_add_impl.mvi.QuestionnaireAddSideEffect
import ru.therapyapp.feature_questionnaire_add_impl.mvi.QuestionnaireAddViewModel

@Composable
fun QuestionnaireScreen(
    viewModel: QuestionnaireAddViewModel,
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = {
        handleSideEffect(
            it,
            context
        )
    })

    QuestionnaireView(
        patients = state.patients,
        questionnaire = state.questionnaire,
        onEvent = { viewModel.dispatch(it) }
    )
}

fun handleSideEffect(effect: QuestionnaireAddSideEffect, activity: AppCompatActivity) {
    when(effect) {
        QuestionnaireAddSideEffect.Finish -> {
            activity.finish()
        }
        is QuestionnaireAddSideEffect.ShowMessage -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun QuestionnaireView(
    patients: List<Patient>,
    questionnaire: Questionnaire,
    onEvent: (QuestionnaireAddEvent) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedPatientName = rememberSaveable { mutableStateOf("Для всех") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Создание анкеты") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(QuestionnaireAddEvent.OnArrowBackClick) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 120.dp, vertical = 30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                value = questionnaire.name,
                label = { Text("Название анкета") },
                onValueChange = {  onEvent(QuestionnaireAddEvent.ChangeName(it)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(id = R.color.main_50),
                    trailingIconColor = colorResource(id = R.color.icon_color),
                    focusedLabelColor = colorResource(id = R.color.font_color),
                    unfocusedLabelColor = colorResource(id = R.color.font_color),
                    cursorColor = colorResource(id = R.color.font_color),
                    focusedIndicatorColor = colorResource(id = R.color.main),
                    unfocusedIndicatorColor = colorResource(id = R.color.main),
                    disabledTrailingIconColor = colorResource(id = R.color.icon_color),
                    disabledTextColor = colorResource(id = R.color.font_color),
                    disabledIndicatorColor = colorResource(id = R.color.main),
                    disabledLabelColor = colorResource(id = R.color.font_color)
                ),
            )

            if (questionnaire.questions.isNotEmpty()) {
                questionnaire.questions.forEachIndexed { index, question ->
                    QuestionView(question = question, questionIndex = index, onEvent = onEvent)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    readOnly = true,
                    value = selectedPatientName.value,
                    onValueChange = { },
                    label = { Text("Для пациента") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
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
                ExposedDropdownMenu(
                    modifier = Modifier
                        .background(color = colorResource(id = R.color.main_50))
                        .fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            selectedPatientName.value = "Для всех"
                            onEvent(QuestionnaireAddEvent.ChangePatient(null))
                            expanded = false
                        }
                    ) {
                        Text(text = "Для всех")
                    }
                    patients.forEach { patient ->
                        val patientName = if (patient.patronymic != null) {
                            "${patient.surname} ${patient.name} ${patient.patronymic}"
                        } else {
                            "${patient.surname} ${patient.name}"
                        }
                        DropdownMenuItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                selectedPatientName.value = patientName
                                onEvent(QuestionnaireAddEvent.ChangePatient(patient.id))
                                expanded = false
                            }
                        ) {
                            Text(text = patientName)
                        }
                    }
                }
            }

            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                onClick = {
                    onEvent(QuestionnaireAddEvent.AddQuestion)
                }
            ) {
                Text(
                    text = "Добавить вопрос",
                    style = TextStyle(color = colorResource(id = R.color.color_white))
                )
            }

            AppButton(
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = colorResource(id = R.color.main),
                onClick = {
                    onEvent(QuestionnaireAddEvent.CreateQuestionnaire)
                }
            ) {
                Text(
                    text = "Создать анкету",
                    style = TextStyle(color = colorResource(id = R.color.color_white))
                )
            }
        }
    }
}