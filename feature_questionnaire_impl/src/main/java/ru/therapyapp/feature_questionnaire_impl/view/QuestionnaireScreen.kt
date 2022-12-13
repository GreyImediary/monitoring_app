package ru.therapyapp.feature_questionnaire_impl.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.data_questionnaire.model.QuestionType
import ru.therapyapp.data_questionnaire.model.Questionnaire
import ru.therapyapp.core_ui.R
import ru.therapyapp.feature_questionnaire_impl.mvi.QuestionnaireEvent
import ru.therapyapp.feature_questionnaire_impl.mvi.QuestionnaireSideEffect
import ru.therapyapp.feature_questionnaire_impl.mvi.QuestionnaireViewModel

@Composable
fun QuestionnaireScreen(
    viewModel: QuestionnaireViewModel,
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current as AppCompatActivity


    viewModel.collectSideEffect(sideEffect = {
        handleSideEffect(
            it,
            context
        )
    })

    state.questionnaire?.let { questionnaire ->
        QuestionnaireView(
            questionnaire = questionnaire,
            onEvent = {
                viewModel.dispatch(it)
            },
        )
    }
}

fun handleSideEffect(effect: QuestionnaireSideEffect, activity: AppCompatActivity) {
    when (effect) {
        QuestionnaireSideEffect.Finish -> {
            activity.finish()
        }
        is QuestionnaireSideEffect.ShowMessage -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnaireView(
    questionnaire: Questionnaire,
    onEvent: (QuestionnaireEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = questionnaire.name) },
                navigationIcon = {
                    IconButton(onClick = { onEvent(QuestionnaireEvent.OnBackClick) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 4.dp)
            .verticalScroll(rememberScrollState())
        ) {
            questionnaire.questions.forEachIndexed { index, question ->
                when (question.questionType) {
                    QuestionType.FIELD -> {
                        TextFieldAnswer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            question = question,
                            questionIndex = index,
                            onEvent = onEvent,
                        )
                    }
                    QuestionType.CHECKBOXES -> {
                        CheckBoxAnswer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            question = question,
                            questionIndex = index,
                            onEvent = onEvent,
                        )
                    }
                    QuestionType.RADIOBUTTONS -> {
                        RadioButtonAnswer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            question = question,
                            questionIndex = index,
                            onEvent = onEvent,
                        )
                    }
                }
            }

            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                onClick = { onEvent(QuestionnaireEvent.SendQuestionnaire) }
            ) {
                Text(text = "Отправить анкету", color = colorResource(id = R.color.color_white))
            }
        }
    }
}