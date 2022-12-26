package ru.therapyapp.feature_current_patient_impl.view

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.util.Pair
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.datepicker.MaterialDatePicker
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R
import ru.therapyapp.core_ui.getPatientScreenHorizontalPadding
import ru.therapyapp.data_core.entity.Sex
import ru.therapyapp.data_core.utils.getStringDateFromLong
import ru.therapyapp.data_core.utils.getStringDateRepresentation
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_current_patient_impl.mvi.CurrentPatientEvent
import ru.therapyapp.feature_current_patient_impl.mvi.CurrentPatientState
import ru.therapyapp.feature_current_patient_impl.mvi.IndexType
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientView(
    patient: Patient,
    state: CurrentPatientState,
    activity: AppCompatActivity,
    onEvent: (CurrentPatientEvent) -> Unit,
) {
    val viewHeight = (LocalConfiguration.current.screenHeightDp / 3).dp
    val localWidth = LocalConfiguration.current.screenWidthDp.dp
    val horizontalPadding = getPatientScreenHorizontalPadding(localWidth)


    var chosenDateString by remember { mutableStateOf("") }

    val patientName = if (!patient.patronymic.isNullOrBlank()) {
        "${patient.surname} ${patient.name}. ${patient.patronymic}."
    } else {
        "${patient.surname} ${patient.name}."
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
                .padding(horizontal = horizontalPadding, vertical = 20.dp),
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
                    comments = state.comments,
                    onEvent = onEvent
                )
            }
            Column(
                Modifier.padding(top = 20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier.padding(end = 18.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .background(
                                    color = colorResource(id = R.color.secondary),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable(
                                    onClick = { expanded = true },
                                    interactionSource = MutableInteractionSource(),
                                    indication = rememberRipple()
                                )
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 8.dp),
                                text = state.currentIndex.name,
                                color = colorResource(id = R.color.color_white)
                            )
                            Icon(
                                imageVector = if (expanded)
                                    Icons.Filled.ArrowDropUp
                                else
                                    Icons.Filled.ArrowDropDown,
                                contentDescription = "",
                                tint = colorResource(id = R.color.color_white)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            IndexType.values().forEach { indexType ->
                                DropdownMenuItem(
                                    text = { Text(text = indexType.name) },
                                    onClick = {
                                        onEvent(CurrentPatientEvent.ChangeIndex(indexType))
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .background(
                                color = colorResource(id = R.color.secondary),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable(
                                onClick = {
                                    val builder = MaterialDatePicker.Builder.dateRangePicker()
                                    val now = Calendar.getInstance()
                                    builder
                                        .setSelection(Pair(now.timeInMillis,
                                            now.timeInMillis))
                                    val picker = builder.build()
                                    picker.addOnPositiveButtonClickListener {
                                        val startDate = getStringDateFromLong(it.first)
                                        val endDate = getStringDateFromLong(it.second)

                                        onEvent(CurrentPatientEvent.ChangeDataPeriod(it.first,
                                            it.second))
                                        chosenDateString = "$startDate-$endDate"
                                    }
                                    picker.show(activity.supportFragmentManager, picker.toString())
                                },
                                interactionSource = MutableInteractionSource(),
                                indication = rememberRipple()
                            )
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 8.dp),
                            text = chosenDateString.ifEmpty {
                                "Выбрать дату"
                            },
                            color = colorResource(id = R.color.color_white)
                        )
                        Icon(
                            imageVector = if (expanded)
                                Icons.Filled.ArrowDropUp
                            else
                                Icons.Filled.ArrowDropDown,
                            contentDescription = "",
                            tint = colorResource(id = R.color.color_white)
                        )

                        if (chosenDateString.isNotEmpty()) {
                            Icon(
                                modifier = Modifier
                                    .clickable(
                                        indication = rememberRipple(),
                                        interactionSource = MutableInteractionSource(),
                                        onClick = {
                                            onEvent(CurrentPatientEvent.OnDeleteDateRange)
                                            chosenDateString = ""
                                        },
                                    ),
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = "",
                                tint = colorResource(id = R.color.color_white)
                            )
                        }
                    }
                }
                Row {
                    AndroidView(
                        modifier = Modifier
                            .height(viewHeight + 50.dp)
                            .weight(1f),
                        factory = { context ->
                            val chart = LineChart(context)

                            val entries = when (state.currentIndex) {
                                IndexType.BVAS -> {
                                    state.bvasIndexes.map {
                                        Entry(
                                            it.date.time.toFloat(),
                                            it.sumValue.toFloat()
                                        )
                                    }
                                }
                                IndexType.BASDAI -> {
                                    state.basdaiIndexes.map {
                                        Entry(
                                            it.date.time.toFloat(),
                                            it.sumValue.toFloat()
                                        )
                                    }
                                }
                                IndexType.ASDAS -> {
                                    state.asdasIndexes.map {
                                        Entry(
                                            it.date.time.toFloat(),
                                            it.sumValue.toFloat()
                                        )
                                    }
                                }
                            }

                            val dataset = LineDataSet(entries, state.currentIndex.name)
                            dataset.color = R.color.font_color
                            dataset.valueTextColor = R.color.font_color
                            chart.data = LineData(dataset)
                            chart.xAxis.valueFormatter = MyXAxisFormatter()

                            chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                                override fun onValueSelected(e: Entry?, h: Highlight?) {
                                    e?.let {
                                        val date = Date(it.x.toLong())
                                        onEvent(CurrentPatientEvent.SelectIndexDataByDate(date))
                                    } ?: onEvent(CurrentPatientEvent.SelectIndexDataByDate(null))
                                }

                                override fun onNothingSelected() {
                                    onEvent(CurrentPatientEvent.SelectIndexDataByDate(null))
                                }

                            })

                            chart.invalidate()
                            chart

                        },
                        update = { view ->
                            val entries = when (state.currentIndex) {
                                IndexType.BVAS -> {
                                    state.bvasIndexes.map {
                                        Entry(
                                            it.date.time.toFloat(),
                                            it.sumValue.toFloat()
                                        )
                                    }
                                }
                                IndexType.BASDAI -> {
                                    state.basdaiIndexes.map {
                                        Entry(
                                            it.date.time.toFloat(),
                                            it.sumValue.toFloat()
                                        )
                                    }
                                }
                                IndexType.ASDAS -> {
                                    state.asdasIndexes.map {
                                        Entry(
                                            it.date.time.toFloat(),
                                            it.sumValue.toFloat()
                                        )
                                    }
                                }
                            }

                            val dataset = LineDataSet(entries, state.currentIndex.name)
                            dataset.color = R.color.font_color
                            dataset.valueTextColor = R.color.font_color
                            view.data = LineData(dataset)
                            view.invalidate()
                        }
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(viewHeight)
                            .background(color = colorResource(id = R.color.main_dark))
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        when (state.currentIndex) {
                            IndexType.BVAS -> {
                                state.selectedBvasIndex?.let {
                                    BvasIndexData(bvasIndex = it)
                                }
                            }
                            IndexType.BASDAI -> {
                                state.selectedBasdaiIndex?.let {
                                    BasdaiIndexData(basdaiIndex = it)
                                }
                            }
                            IndexType.ASDAS -> {
                                state.selectedAsdasIndex?.let {
                                    AsdasIndexData(asdasIndex = it)
                                }
                            }
                        }
                    }

                }
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
                        if (comment.isEmpty()) {
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
    comment: String,
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