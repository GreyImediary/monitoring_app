@file:OptIn(ExperimentalMaterial3Api::class)

package ru.therapyapp.feature_current_patient_impl.view

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_core.entity.Sex
import ru.therapyapp.data_core.utils.getStringDateRepresentation
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_current_patient_impl.mvi.CurrentPatientEvent
import ru.therapyapp.feature_current_patient_impl.mvi.CurrentPatientSideEffect
import ru.therapyapp.feature_current_patient_impl.mvi.CurrentPatientViewModel

@Composable
fun PatientScreen(
    viewModel: CurrentPatientViewModel,
    onEvent: (CurrentPatientEvent) -> Unit
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = { handleSideEffects(context, it) })


    state.patient?.let {
        PatientView(
            patient = it,
            comments = state.comments,
            onEvent = onEvent
        )
    }
}

private fun handleSideEffects(
    activity: AppCompatActivity,
    effect: CurrentPatientSideEffect,
) {
    when(effect) {
        CurrentPatientSideEffect.Finish -> {
            activity.finish()
        }
        is CurrentPatientSideEffect.ShowMessage -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun PatientView(
    patient: Patient,
    comments: List<String>,
    onEvent: (CurrentPatientEvent) -> Unit,
) {
    val viewHeight = (LocalConfiguration.current.screenHeightDp / 3).dp

    val patientName = if (patient.patronymic != null) {
        "${patient.surname} ${patient.name.first()}. ${patient.patronymic?.first()}."
    } else {
        "${patient.surname} ${patient.name.first()}."
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = patientName) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(CurrentPatientEvent.OnBackClick)
                        }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 58.dp, vertical = 20.dp),
        ) {
            Row {
                PatientDataView(
                    modifier = Modifier
                        .padding(end = 50.dp)
                        .weight(1f),
                    patient = patient
                )

                CommentsView(
                    modifier = Modifier
                        .height(viewHeight)
                        .weight(1f),
                    comments = comments,
                    onEvent = onEvent
                )
            }
            Row(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Box(modifier = Modifier
                    .height(viewHeight)
                    .weight(1f)
                    .background(Color.Red)
                )
                Box(modifier = Modifier
                    .height(viewHeight)
                    .weight(1f)
                    .background(Color.Green)
                )
            }

            Row {
                Box(modifier = Modifier
                    .height(viewHeight)
                    .weight(1f)
                    .background(Color.Green)
                )
                Box(modifier = Modifier
                    .height(viewHeight)
                    .weight(1f)
                    .background(Color.Red)
                )
            }
        }
    }

}

@Composable
private fun PatientDataView(
    modifier: Modifier = Modifier,
    patient: Patient,
) {
    val sex = when (patient.sex) {
        Sex.MALE -> "Мужской"
        Sex.FEMALE -> "Женский"
    }

    Column(
        modifier = modifier
            .background(color = colorResource(id = R.color.main_50))
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Номер карты: ${patient.patientCardNumber}",
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp

            )
        )

        Divider(color = colorResource(id = R.color.main))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp),
            text = "Пол: $sex"
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
            text = "Дата рождения: ${patient.birthDate.getStringDateRepresentation()}"
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
            text = "Номер телефона: ${patient.phoneNumber}"
        )
        patient.additionalPhoneNumber?.let {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
                text = "Доп. номер телефона: $it"
            )
        }
        patient.email?.let {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = "Почта: $it"
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CommentsView(
    modifier: Modifier = Modifier,
    comments: List<String>,
    onEvent: (CurrentPatientEvent) -> Unit,
) {
    var isDialogOpened by remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf("") }


        if (isDialogOpened) {
        AlertDialog(
            onDismissRequest = { isDialogOpened = false },
            title = { Text(text = "Добавить комментарий") },
            text = {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = comment,
                    label = { Text("Комментарий") },
                    onValueChange = { comment = it },
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
            },
            confirmButton = {
                AppButton(
                    onClick = {
                        if(comment.isEmpty()) {
                            onEvent(CurrentPatientEvent.OnMessageShow("Введите текст. Поле не может быть пустым"))
                        } else {
                            onEvent(CurrentPatientEvent.AddComment(comment))
                            isDialogOpened = false
                        }
                    },
                ) {
                    Text("Добавить", color = colorResource(id = R.color.color_white))
                }
            },
            dismissButton = {
                AppButton(
                    onClick = {
                        isDialogOpened = false
                    },
                ) {
                    Text("Отменить", color = colorResource(id = R.color.color_white))
                }
            }
        )
    }

    Column(
        modifier = modifier
            .background(color = colorResource(id = R.color.main_50))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Комментарии",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                )
            )

            IconButton(
                onClick = {
                    isDialogOpened = true
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }

        Divider(color = colorResource(id = R.color.main))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 14.dp, start = 16.dp, end = 16.dp, bottom = 2.dp)
        ) {
            LazyColumn {
                items(comments) { comment ->
                    CommentView(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                        comment = comment
                    )
                }
            }
        }
    }
}

@Composable
private fun CommentView(
    modifier: Modifier = Modifier,
    comment: String
) {
    Text(
        modifier = modifier
            .background(
                color = colorResource(id = R.color.color_white),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = comment
    )
}