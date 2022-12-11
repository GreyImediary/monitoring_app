package ru.therapyapp.feature_answered_questionnaire_impl.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnswered
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_core.utils.getStringDateRepresentation

@Composable
fun QuestionnaireAnsweredView(
    questionnaireAnswered: QuestionnaireAnswered,
) {
    val patient = questionnaireAnswered.patient
    val patientName = if (patient.patronymic != null) {
        "${patient.surname} ${patient.name} ${patient.patronymic}"
    } else {
        "${patient.surname} ${patient.name}"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.main_50),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 16.dp),
    ) {

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            text = questionnaireAnswered.questionnaire.name,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Divider(
            color = colorResource(id = R.color.main)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = "Пациент: $patientName", fontWeight = FontWeight.Bold)
            Text(text = "Дата: ${questionnaireAnswered.date.getStringDateRepresentation()}")
        }

        questionnaireAnswered.answers.forEach {
            Divider(
                color = colorResource(id = R.color.main)
            )
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                text = it.title,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = "Ответ: ${it.answer}"
            )
        }
    }
}