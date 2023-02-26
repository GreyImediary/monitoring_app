package ru.therapyapp.feature_questionnaire_add_impl.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.therapyapp.data_questionnaire.model.Question
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_questionnaire.model.QuestionType
import ru.therapyapp.feature_questionnaire_add_impl.mvi.QuestionnaireAddEvent

@Composable
fun QuestionView(
    question: Question,
    questionIndex: Int,
    onEvent: (QuestionnaireAddEvent) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val questionTitle = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
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
                    .padding(start = 8.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = "Тип вопроса: ${question.questionType.questionTypeName}",
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
                QuestionType.values().forEach { indexType ->
                    DropdownMenuItem(
                        text = { Text(text = indexType.questionTypeName) },
                        onClick = {
                            onEvent(QuestionnaireAddEvent.ChangeQuestionType(indexType,
                                questionIndex))
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))
            if (question.questionType == QuestionType.CHECKBOXES || question.questionType == QuestionType.RADIOBUTTONS) {
                Row(
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.secondary),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable(
                            onClick = { onEvent(QuestionnaireAddEvent.AddOption(questionIndex)) },
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple()
                        )
                        .padding(start = 8.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        text = "Добавить опцию",
                        color = colorResource(id = R.color.color_white)
                    )
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        tint = colorResource(id = R.color.color_white)
                    )
                }
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            value = questionTitle.value,
            label = { Text("Заголовок вопроса") },
            onValueChange = {
                questionTitle.value = it
                onEvent(QuestionnaireAddEvent.ChangeQuestionTitle(it, questionIndex))
            },
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

        if (question.questionType == QuestionType.CHECKBOXES || question.questionType == QuestionType.RADIOBUTTONS) {
            Text(text = "Опции", fontWeight = FontWeight.Bold)

            Column(modifier = Modifier
                .padding(bottom = 20.dp, top = 8.dp)
            ) {
                question.options.forEachIndexed { index, option ->
                    val optionTitle = rememberSaveable { mutableStateOf("") }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(bottom = 8.dp, end = 8.dp)
                                .weight(0.9f),
                            value = optionTitle.value,
                            label = { Text("Заголовок опции ") },
                            onValueChange = {
                                optionTitle.value = it
                                onEvent(
                                    QuestionnaireAddEvent.ChangeOptionDescription(it,
                                    questionIndex,
                                    index)
                                )
                            },
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

                        IconButton(
                            modifier = Modifier.weight(0.1f),
                            onClick = {
                                onEvent(QuestionnaireAddEvent.DeleteOption(questionIndex,
                                    index))
                            }
                        ) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = { onEvent(QuestionnaireAddEvent.DeleteQuestion(questionIndex)) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = ""
                )
            }
        }
    }

}