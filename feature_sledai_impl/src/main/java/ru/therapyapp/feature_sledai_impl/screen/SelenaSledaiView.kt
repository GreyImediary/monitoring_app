package ru.therapyapp.feature_sledai_impl.screen

import ru.therapyapp.feature_sledai_impl.mvi.SelenaSledaiEvent
import ru.therapyapp.feature_sledai_impl.mvi.SelenaSledaiState

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SelenaSledaiView(state: SelenaSledaiState, onEvent: (SelenaSledaiEvent) -> Unit) {
    val scroll = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Индекс SELENA SLEDAI") },
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
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = "Отметьте симптомы, которые присутствуют на момент осмотра или проявляются в течение последних 10 дней"
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Эпилептический приступ",
                point = 8,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Психоз",
                point = 8,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Органические мозговые синдромы",
                point = 8,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Зрительные нарушения ",
                point = 8,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Расстройства со стороны черепно-мозговых нервов",
                point = 8,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Головная боль",
                point = 8,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Нарушение мозгового кровообращения",
                point = 8,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Васкулит",
                point = 8,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Артрит",
                point = 4,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Миозит",
                point = 4,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Цилиндрурия",
                point = 4,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Гематурия",
                point = 4,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Протеинурия",
                point = 4,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Пиурия",
                point = 4,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Высыпания",
                point = 2,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Алопеция",
                point = 2,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Язвы слизистых оболочек ",
                point = 2,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Плеврит",
                point = 2,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Перикардит",
                point = 2,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Низкий уровень комплемента ",
                point = 2,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Повышение уровня антител к ДНК",
                point = 2,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Лихорадка",
                point = 1,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 4.dp),
                answer = "Тромбоцитопения",
                point = 1,
                onEvent = onEvent
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 8.dp),
                answer = "Лейкопения",
                point = 1,
                onEvent = onEvent
            )


            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Результат: ${state.sumValue}"
            )

            AppButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onEvent(SelenaSledaiEvent.OnSendIndex)
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
    answer: String,
    point: Int,
    onEvent: (SelenaSledaiEvent) -> Unit,
) {
    val isChecked = remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .clickable {
                isChecked.value = !isChecked.value
                if (isChecked.value) {
                    onEvent(SelenaSledaiEvent.OnAddSledai(answer, point))
                } else {
                    onEvent(SelenaSledaiEvent.OnRemoveSledai(answer, point))
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.padding(end = 6.dp),
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                if (it) {
                    onEvent(SelenaSledaiEvent.OnAddSledai(answer, point))
                } else {
                    onEvent(SelenaSledaiEvent.OnRemoveSledai(answer, point))
                }
            },
        )

        Text(text = answer)
    }
}