package ru.therapyapp.feature_answered_questionnaire_impl.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.core_ui.getLargeHorizontalPadding
import ru.therapyapp.core_ui.getMediumHorizontalPadding
import ru.therapyapp.data_questionnaire_answered.model.QuestionnaireAnswered
import ru.therapyapp.feature_answered_questionnaire_impl.mvi.QuestionnaireAnsweredEvent
import ru.therapyapp.feature_answered_questionnaire_impl.mvi.QuestionnaireAnsweredSideEffect
import ru.therapyapp.feature_answered_questionnaire_impl.mvi.QuestionnaireAnsweredViewModel

@Composable
fun QuestionnaireAnsweredScreen(
    viewModel: QuestionnaireAnsweredViewModel,
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = {
        handleSideEffect(
            it,
            context
        )
    })

    QuestionnaireAnsweredView(
        questionnaires = state.questionnairesAnswered,
        onEvent = { viewModel.dispatch(it) }
    )
}

fun handleSideEffect(effect: QuestionnaireAnsweredSideEffect, activity: AppCompatActivity) {
    when (effect) {
        QuestionnaireAnsweredSideEffect.Finish -> {
            activity.finish()
        }
        is QuestionnaireAnsweredSideEffect.ShowMessage -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnaireAnsweredView(
    questionnaires: List<QuestionnaireAnswered>,
    onEvent: (QuestionnaireAnsweredEvent) -> Unit,
) {
    val localConfigWidth = LocalConfiguration.current.screenWidthDp
    val horizontalDp = getMediumHorizontalPadding(localConfigWidth.dp)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Ответы на анкету") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(QuestionnaireAnsweredEvent.OnArrowBackClick) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = horizontalDp, vertical = 30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            questionnaires.forEach {
                QuestionnaireAnsweredView(questionnaireAnswered = it)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}