package ru.therapyapp.feature_basdai_impl.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.feature_basdai_impl.mvi.BasdaiEvent
import ru.therapyapp.feature_basdai_impl.mvi.BasdaiState
import ru.therapyapp.feature_basdai_impl.mvi.QuestionNumber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasdaiView(state: BasdaiState, onEvent: (BasdaiEvent) -> Unit) {
    val scroll = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Индекс BASDAI") },
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
                titleText = "1. Как бы Вы расценили уровень общей слабости (утомляемости) за последнюю неделю?",
                onValueChangeFinished = {
                    onEvent(BasdaiEvent.SendAnswer(QuestionNumber.ONE, it))
                }
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 16.dp),
                titleText = "2. Как бы Вы расценили уровень боли в шее, спине или тазобедренных суставах за последнюю неделю?",
                onValueChangeFinished = {
                    onEvent(BasdaiEvent.SendAnswer(QuestionNumber.TWO, it))
                }
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 16.dp),
                titleText = "3. Как бы Вы расценили уровень боли (или степень припухлости) в суставах (помимо шеи, спины или тазобедренных суставов) за последнюю неделю?",
                onValueChangeFinished = {
                    onEvent(BasdaiEvent.SendAnswer(QuestionNumber.THREE, it))
                }
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 16.dp),
                titleText = "4. Как бы Вы расценили степень неприятных ощущений, возникающих при дотрагивании до каких-либо болезненных областей или давлении на них (за последнюю неделю)?",
                onValueChangeFinished = {
                    onEvent(BasdaiEvent.SendAnswer(QuestionNumber.FOUR, it))
                }
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 16.dp),
                titleText = "5. Как бы Вы расценили степень выраженности утренней скованности, возникающей после просыпания (за последнюю неделю)?",
                onValueChangeFinished = {
                    onEvent(BasdaiEvent.SendAnswer(QuestionNumber.FIVE, it))
                }
            )

            QuestionView(
                modifier = Modifier.padding(bottom = 16.dp),
                titleText = "6. Как долго длится утренняя скованность, возникающая после просыпания\n(За последнюю неделю. От \"От не было\" до \"2 часа и больше\")?",
                onValueChangeFinished = {
                    onEvent(BasdaiEvent.SendAnswer(QuestionNumber.SIX, it))
                }
            )

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Результат: ${state.sumValue}"
            )

            AppButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onEvent(BasdaiEvent.SendIndex)
                }
            ) {
                Text(
                    text = "Отправить",
                    style = TextStyle(color = colorResource(id = ru.therapyapp.core_ui.R.color.color_white))
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