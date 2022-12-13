package ru.therapyapp.feature_questionnaire_impl.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_questionnaire.model.Question
import ru.therapyapp.feature_questionnaire_impl.mvi.QuestionnaireEvent

@Composable
fun TextFieldAnswer(
    modifier: Modifier = Modifier,
    question: Question,
    questionIndex: Int,
    onEvent: (QuestionnaireEvent) -> Unit,
) {
    val answerText = rememberSaveable { mutableStateOf("") }

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
            modifier = Modifier.padding(bottom = 8.dp),
            text = "${questionIndex + 1}. ${question.title}"
        )

        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold,
            text = "Введите ответа",
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            value = answerText.value,
            label = { Text("Ответ") },
            onValueChange = {
                answerText.value = it
                onEvent(QuestionnaireEvent.SetAnswer(questionIndex, it))
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
    }
}