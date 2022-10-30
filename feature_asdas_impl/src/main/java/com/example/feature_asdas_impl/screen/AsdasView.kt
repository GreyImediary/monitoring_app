package com.example.feature_asdas_impl.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data_asdas.model.SrbSoeType
import com.example.feature_asdas_impl.mvi.AsdasEvent
import com.example.feature_asdas_impl.mvi.AsdasState
import com.example.feature_asdas_impl.mvi.QuestionNumber
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AsdasView(state: AsdasState, onEvent: (AsdasEvent) -> Unit) {
    val scroll = rememberScrollState()

    val srbSoeValue = rememberSaveable { mutableStateOf("0") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Индекс ASDAS") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuestionView(
                modifier = Modifier.padding(bottom = 16.dp),
                titleText = "1. Как бы Вы расценили боль в спине?",
                onValueChangeFinished = {
                    onEvent(AsdasEvent.OnSendAnswer(QuestionNumber.ONE, it))
                }
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 16.dp),
                titleText = "2. Как бы Вы расценили продолжительность утренней скованности?",
                onValueChangeFinished = {
                    onEvent(AsdasEvent.OnSendAnswer(QuestionNumber.TWO, it))
                }
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 16.dp),
                titleText = "3. Общая оценка активности заболевания?",
                onValueChangeFinished = {
                    onEvent(AsdasEvent.OnSendAnswer(QuestionNumber.THREE, it))
                }
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 16.dp),
                titleText = "4. Как бы Вы расценили боль/припухлость периферических суставов?",
                onValueChangeFinished = {
                    onEvent(AsdasEvent.OnSendAnswer(QuestionNumber.FOUR, it))
                }
            )

            Row {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = state.srbSoeType == SrbSoeType.SRB,
                            onClick = {
                                onEvent(AsdasEvent.OnChangeSrbSoeType(SrbSoeType.SRB))
                                srbSoeValue.value = "0"
                                onEvent(AsdasEvent.OnSendSrbSoeValue(0.0))
                            },
                            role = Role.RadioButton,
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple()
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = state.srbSoeType == SrbSoeType.SRB,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.secondary)
                        )
                    )
                    Text(
                        text = "СРБ",
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = state.srbSoeType == SrbSoeType.SOE,
                            onClick = {
                                onEvent(AsdasEvent.OnChangeSrbSoeType(SrbSoeType.SOE))
                                srbSoeValue.value = "0"
                                onEvent(AsdasEvent.OnSendSrbSoeValue(0.0))
                            },
                            role = Role.RadioButton,
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple()
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = state.srbSoeType == SrbSoeType.SOE,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.secondary)
                        )
                    )
                    Text(
                        text = "СОЭ",
                    )
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                value = srbSoeValue.value,
                onValueChange = {
                    val formattedValue = it.replace(Regex("""[^.0-9]"""), "")
                    srbSoeValue.value = formattedValue
                    onEvent(
                        AsdasEvent.OnSendSrbSoeValue(
                            if (formattedValue.isNotBlank()) {
                                formattedValue.trim().toDouble()
                            } else {
                                0.0
                            }
                        )
                    )
                },
                label = { Text("Значение") },
                trailingIcon = {
                    Text(text = when (state.srbSoeType) {
                        SrbSoeType.SRB -> "мг/л"
                        SrbSoeType.SOE -> "мм/ч(по Вестергрену)"
                    }
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    backgroundColor = colorResource(id = R.color.main_50),
                    trailingIconColor = colorResource(id = R.color.icon_color),
                    focusedLabelColor = colorResource(id = R.color.font_color),
                    unfocusedLabelColor = colorResource(id = R.color.font_color),
                    focusedTrailingIconColor = colorResource(id = R.color.icon_color),
                    focusedIndicatorColor = colorResource(id = R.color.main),
                    unfocusedIndicatorColor = colorResource(id = R.color.main),
                    disabledTrailingIconColor = colorResource(id = R.color.icon_color),
                    disabledTextColor = colorResource(id = R.color.font_color),
                    disabledIndicatorColor = colorResource(id = R.color.main),
                    disabledLabelColor = colorResource(id = R.color.font_color)

                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
            )

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Результат: ${state.sumValue}"
            )

            AppButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onEvent(AsdasEvent.OnSendIndex)
                }
            ) {
                Text(
                    text = "Отправить",
                    style = TextStyle(color = colorResource(id = R.color.color_white))
                )
            }
        }
    }
}


@Composable
private fun QuestionView(
    modifier: Modifier = Modifier,
    titleText: String,
    onValueChangeFinished: (Int) -> Unit,
) {
    var sliderPosition by remember { mutableStateOf(0f) }

    Column(
        modifier = modifier,
    ) {
        Text(text = titleText, fontWeight = FontWeight.Medium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "0", modifier = Modifier.weight(0.5f))
            Slider(
                modifier = Modifier.weight(9f),
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 0f..10f,
                steps = 9,
                onValueChangeFinished = { onValueChangeFinished(sliderPosition.toInt()) }
            )
            Text(text = "10", modifier = Modifier.weight(0.5f), textAlign = TextAlign.End)
        }
        Text(text = "Ответ: ${sliderPosition.toInt()} баллов")
    }
}