package ru.therapyapp.feature_questionnaire_impl.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_questionnaire.model.Question
import ru.therapyapp.feature_questionnaire_impl.mvi.QuestionnaireEvent

@Composable
fun CheckBoxAnswer(
    modifier: Modifier = Modifier,
    question: Question,
    questionIndex: Int,
    onEvent: (QuestionnaireEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.main),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = "${questionIndex + 1}. ${question.title}"
        )

        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold,
            text = "Варианты ответа:"
        )

        question.options.forEach {
            CheckBoxAnswerOption(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 4.dp),
                questionIndex = questionIndex,
                optionTitle = it.description,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun CheckBoxAnswerOption(
    modifier: Modifier = Modifier,
    questionIndex: Int,
    optionTitle: String,
    onEvent: (QuestionnaireEvent) -> Unit,
) {
    val isChecked = rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = {
                    isChecked.value = !isChecked.value

                    if (isChecked.value) {
                        onEvent(QuestionnaireEvent.AddCheckBoxAnswer(questionIndex, optionTitle))
                    } else {
                        onEvent(QuestionnaireEvent.DeleteCheckBoxAnswer(questionIndex, optionTitle))
                    }
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Checkbox(
            modifier = Modifier.padding(end = 10.dp),
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it

                if (it) {
                    onEvent(QuestionnaireEvent.AddCheckBoxAnswer(questionIndex, optionTitle))
                } else {
                    onEvent(QuestionnaireEvent.DeleteCheckBoxAnswer(questionIndex, optionTitle))
                }
            }
        )

        Text(text = optionTitle)
    }
}