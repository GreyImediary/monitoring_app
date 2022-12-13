package ru.therapyapp.feature_questionnaire_impl.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_questionnaire.model.Question
import ru.therapyapp.feature_questionnaire_impl.mvi.QuestionnaireEvent

@Composable
fun RadioButtonAnswer(
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
        val selectedValue = rememberSaveable { mutableStateOf("") }

        val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
        val onChangeState: (String) -> Unit = {
            selectedValue.value = it
            onEvent(QuestionnaireEvent.SetAnswer(questionIndex, it))
        }

        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "${questionIndex + 1}. ${question.title}"
        )

        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold,
            text = "Варианты ответа:",
        )

        question.options.forEach { option ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = isSelectedItem(option.description),
                        onClick = { onChangeState(option.description) },
                        role = Role.RadioButton,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                RadioButton(
                    modifier = Modifier.padding(end = 8.dp),
                    selected = isSelectedItem(option.description),
                    onClick = { onChangeState(option.description) },
                )

                Text(text = option.description)
            }
        }
    }
}