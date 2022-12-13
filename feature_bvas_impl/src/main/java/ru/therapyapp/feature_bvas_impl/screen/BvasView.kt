package ru.therapyapp.feature_bvas_impl.screen

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
import ru.therapyapp.data_bvas.model.BvasIndex
import ru.therapyapp.feature_bvas_impl.mvi.BvasEvent
import ru.therapyapp.feature_bvas_impl.mvi.BvasState
import ru.therapyapp.feature_bvas_impl.mvi.QuestionNumber
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BvasView(state: BvasState, onEvent: (BvasEvent) -> Unit) {
    val scroll = rememberScrollState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Индекс BVAS") },
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
                text = "Отметьте симптомы, имеющиеся на момент осмотра, а также появившиеся или прогрессировавшие в течение последнего месяца"
            )
            QuestionTitleView(title = "I. Системные проявления:") {
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Миалгии /артралгии /артрит",
                        value = 1,
                    ),
                    questionNumber = QuestionNumber.ONE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Лихорадка (<38.5°C)",
                        value = 1,
                    ),
                    questionNumber = QuestionNumber.ONE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Лихорадка (>38.5°C)",
                        value = 2
                    ),
                    questionNumber = QuestionNumber.ONE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Потеря массы тела (<2 кг)",
                        value = 2
                    ),
                    questionNumber = QuestionNumber.ONE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Потеря массы тела (>2 кг)",
                        value = 3
                    ),
                    questionNumber = QuestionNumber.ONE,
                    onEvent = onEvent,
                )
            }

            QuestionTitleView(title = "II. Кожные покровы:") {
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Пурпура/другой васкулит кожи",
                        value = 2
                    ),
                    questionNumber = QuestionNumber.TWO,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Язвы",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.TWO,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Гангрена",
                        value = 6
                    ),
                    questionNumber = QuestionNumber.TWO,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Множеств. гангрены пальцев",
                        value = 6
                    ),
                    questionNumber = QuestionNumber.TWO,
                    onEvent = onEvent,
                )
            }

            QuestionTitleView(title = "III. Слизистые оболочки/глаза:") {
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Язвы полости рта",
                        value = 1
                    ),
                    questionNumber = QuestionNumber.THREE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Язвы половых органов",
                        value = 1
                    ),
                    questionNumber = QuestionNumber.THREE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = " Конъюнктивит",
                        value = 1
                    ),
                    questionNumber = QuestionNumber.THREE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Эписклерит /склерит",
                        2
                    ),
                    questionNumber = QuestionNumber.THREE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Увеит",
                        value = 6,
                    ),
                    questionNumber = QuestionNumber.THREE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Отек /геморрагии сетчатки",
                        value = 6
                    ),
                    questionNumber = QuestionNumber.THREE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Ретро-орбитальная гранулема",
                        value = 6
                    ),
                    questionNumber = QuestionNumber.THREE,
                    onEvent = onEvent,
                )
            }

            QuestionTitleView(title = "IV. ЛОР-органы:") {
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Выделения /заложенность носа",
                        value = 2,
                    ),
                    questionNumber = QuestionNumber.FOUR,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Синусит",
                        value = 2,
                    ),
                    questionNumber = QuestionNumber.FOUR,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Носовое кровотечение",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.FOUR,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Кровяные корочки в носу",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.FOUR,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Выделения из ушей",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.FOUR,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Средний отит",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.FOUR,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Глухота",
                        value = 6,
                    ),
                    questionNumber = QuestionNumber.FOUR,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Охриплость /ларингит",
                        value = 2,
                    ),
                    questionNumber = QuestionNumber.FOUR,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Стеноз гортани",
                        value = 6,
                    ),
                    questionNumber = QuestionNumber.FOUR,
                    onEvent = onEvent,
                )
            }

            QuestionTitleView(title = "V. Легкие:") {
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Одышка /астма",
                        value = 2,
                    ),
                    questionNumber = QuestionNumber.FIVE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Узелки или фиброз",
                        value = 2,
                    ),
                    questionNumber = QuestionNumber.FIVE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Инфильтрат",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.FIVE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Кровохаркание",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.FIVE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Плевральный выпот /плеврит",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.FIVE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Легочное кровотечение",
                        value = 6,
                    ),
                    questionNumber = QuestionNumber.FIVE,
                    onEvent = onEvent,
                )
            }

            QuestionTitleView(title = "VI. Сердечно-сосудистая система:") {
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Шумы",
                        value = 2,
                    ),
                    questionNumber = QuestionNumber.SIX,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Отсутствие пульса",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.SIX,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Аортальная недостаточность",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.SIX,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Перикардит",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.SIX,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "ОИМ",
                        value = 6,
                    ),
                    questionNumber = QuestionNumber.SIX,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "НК /кардиомиопатия",
                        value = 6,
                    ),
                    questionNumber = QuestionNumber.SIX,
                    onEvent = onEvent,
                )
            }

            QuestionTitleView(title = "VII. Желудочно-кишечный тракт:") {
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Боль в животе",
                        value = 3,
                    ),
                    questionNumber = QuestionNumber.SEVEN,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Кровавая диарея",
                        value = 6,
                    ),
                    questionNumber = QuestionNumber.SEVEN,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Инфаркт кишечника",
                        value = 9,
                    ),
                    questionNumber = QuestionNumber.SEVEN,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Панкреатит /перфорация ж.п.",
                        value = 9,
                    ),
                    questionNumber = QuestionNumber.SEVEN,
                    onEvent = onEvent,
                )
            }

            QuestionTitleView(title = "VIII. Почки:") {
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Диастолическое АД >90 мм. рт. ст.",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.EIGHT,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Протеинурия (>1г или >0.2 г/с)",
                        value = 4,
                    ),
                    questionNumber = QuestionNumber.EIGHT,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Гематурия (>1эр. или >0.2 эр./мл)",
                        value = 8,
                    ),
                    questionNumber = QuestionNumber.EIGHT,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Креатинин 125–249 мкмоль/л",
                        value = 8,
                    ),
                    questionNumber = QuestionNumber.EIGHT,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Креатинин 250–499 мкмоль/л",
                        value = 10,
                    ),
                    questionNumber = QuestionNumber.EIGHT,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = " Креатинин > 500 мкмоль/л",
                        value = 12,
                    ),
                    questionNumber = QuestionNumber.EIGHT,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "БПГН",
                        value = 12,
                    ),
                    questionNumber = QuestionNumber.EIGHT,
                    onEvent = onEvent,
                )
            }

            QuestionTitleView(title = "IX. Нервная система:") {
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Органические нарушения, деменция",
                        value = 3,
                    ),
                    questionNumber = QuestionNumber.NINE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Периферическая нейропатия",
                        value = 6,
                    ),
                    questionNumber = QuestionNumber.NINE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Множеств. двигательн. мононеврит",
                        value = 9,
                    ),
                    questionNumber = QuestionNumber.NINE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Судороги",
                        value = 9,
                    ),
                    questionNumber = QuestionNumber.NINE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Инсульт",
                        value = 9,
                    ),
                    questionNumber = QuestionNumber.NINE,
                    onEvent = onEvent,
                )
                QuestionView(
                    modifier = Modifier.padding(bottom = 4.dp),
                    questionAnswer = BvasIndex.QuestionAnswer(
                        title = "Поражение спинного мозга",
                        value = 9,
                    ),
                    questionNumber = QuestionNumber.NINE,
                    onEvent = onEvent,
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Результат: ${state.sumValue}"
            )

            AppButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onEvent(BvasEvent.OnSendIndex)
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
private fun QuestionTitleView(
    title: String,
    content: @Composable () -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = title,
            style = TextStyle(fontWeight = FontWeight.Medium)
        )

        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun QuestionView(
    modifier: Modifier = Modifier,
    questionAnswer: BvasIndex.QuestionAnswer,
    questionNumber: QuestionNumber,
    onEvent: (BvasEvent) -> Unit,
) {
    val isChecked = remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .clickable {
                isChecked.value = !isChecked.value
                if (isChecked.value) {
                    onEvent(BvasEvent.OnAddBvasAnswer(questionNumber, questionAnswer))
                } else {
                    onEvent(BvasEvent.OnDeleteBvasAnswer(questionNumber, questionAnswer))
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
                    onEvent(BvasEvent.OnAddBvasAnswer(questionNumber, questionAnswer))
                } else {
                    onEvent(BvasEvent.OnDeleteBvasAnswer(questionNumber, questionAnswer))
                }
            },
        )

        Text(text = questionAnswer.title)
    }
}