package ru.therapyapp.feature_current_patient_impl.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.therapyapp.data_asdas.model.AsdasIndex
import ru.therapyapp.data_asdas.model.SrbSoeType
import ru.therapyapp.data_bvas.model.BvasIndex
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_basdai.model.BasdaiIndex
import ru.therapyapp.data_core.utils.getStringDateRepresentation

@Composable
fun BvasIndexData(bvasIndex: BvasIndex) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(end = 20.dp),
            text = bvasIndex.date.getStringDateRepresentation(),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.color_white)
        )

        Text(
            text = "Значение: ${bvasIndex.sumValue}",
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.color_white)
        )
    }

    Divider(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp))

    Text(
        text = "I. Системные проявления",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${bvasIndex.question1.joinToString(separator = ", ") { it.title }}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "II. Кожные покровы",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${bvasIndex.question2.joinToString(separator = ", ") { it.title }}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "III. Слизистые оболочки/глаза",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${bvasIndex.question3.joinToString(separator = ", ") { it.title }}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "IV. ЛОР-органы",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${bvasIndex.question4.joinToString(separator = ", ") { it.title }}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "V. Легкие",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${bvasIndex.question5.joinToString(separator = ", ") { it.title }}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "VI. Сердечно-сосудистая система",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${bvasIndex.question6.joinToString(separator = ", ") { it.title }}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "VII. Желудочно-кишечный тракт",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${bvasIndex.question7.joinToString(separator = ", ") { it.title }}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "VIII. Почки",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${bvasIndex.question8.joinToString(separator = ", ") { it.title }}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "IX. Нервная система",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${bvasIndex.question9.joinToString(separator = ", ") { it.title }}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )
}

@Composable
fun AsdasIndexData(asdasIndex: AsdasIndex) {
    val srbSoeText = when (asdasIndex.srbSoeType) {
        SrbSoeType.SRB -> "${asdasIndex.srbSoeValue} мг/л"
        SrbSoeType.SOE -> "${asdasIndex.srbSoeValue} мм/ч(по Вестергрену)"
    }

    val srbSoeTitle = when(asdasIndex.srbSoeType) {
        SrbSoeType.SRB -> "СРБ"
        SrbSoeType.SOE -> "СОЭ"
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(end = 20.dp),
            text = asdasIndex.date.getStringDateRepresentation(),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.color_white)
        )

        Text(
            text = "Значение: ${asdasIndex.sumValue}",
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.color_white)
        )
    }

    Divider(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp))

    Text(
        text = "1. Как бы Вы расценили боль в спине?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${asdasIndex.question1}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "2. Как бы Вы расценили продолжительность утренней скованности?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${asdasIndex.question2}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "3. Общая оценка активности заболевания?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${asdasIndex.question3}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "4. Как бы Вы расценили боль/припухлость периферических суставов?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${asdasIndex.question4}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = srbSoeTitle,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: $srbSoeText",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )
}

@Composable
fun BasdaiIndexData(basdaiIndex: BasdaiIndex) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(end = 20.dp),
            text = basdaiIndex.date.getStringDateRepresentation(),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.color_white)
        )

        Text(
            text = "Значение: ${basdaiIndex.sumValue}",
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.color_white)
        )
    }

    Divider(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp))

    Text(
        text = "1. Как бы Вы расценили уровень общей слабости (утомляемости) за последнюю неделю?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${basdaiIndex.question1Value}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "2. Как бы Вы расценили уровень боли в шее, спине или тазобедренных суставах за последнюю неделю?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${basdaiIndex.question2Value}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "3. Как бы Вы расценили уровень боли (или степень припухлости) в суставах (помимо шеи, спины или тазобедренных суставов) за последнюю неделю?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${basdaiIndex.question3Value}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "4. Как бы Вы расценили степень неприятных ощущений, возникающих при дотрагивании до каких-либо болезненных областей или давлении на них (за последнюю неделю)?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${basdaiIndex.question4Value}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "5. Как бы Вы расценили степень выраженности утренней скованности, возникающей после просыпания (за последнюю неделю)?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${basdaiIndex.question5Value}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )

    Text(
        text = "6. Как долго длится утренняя скованность, возникающая после просыпания\n" +
                "(За последнюю неделю. От \"От не было\" до \"2 часа и больше\")?",
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.color_white)
    )
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Ответ: ${basdaiIndex.question6Value}",
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.color_white)
    )
}