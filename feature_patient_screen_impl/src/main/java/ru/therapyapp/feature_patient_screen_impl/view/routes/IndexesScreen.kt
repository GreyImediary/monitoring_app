package ru.therapyapp.feature_patient_screen_impl.view.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.therapyapp.core_ui.R
import ru.therapyapp.data_request.api.entity.Request
import ru.therapyapp.data_request.api.entity.RequestStatus
import ru.therapyapp.data_request.api.entity.RequestUpdateBody
import ru.therapyapp.feature_patient_screen_impl.mvi.PatientScreenEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexesRoute(
    onMenuClick: () -> Unit,
    onEvent: (PatientScreenEvent) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Индексы") },
                navigationIcon = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                IndexCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    title = "BASDAI",
                    questionCount = 6,
                    onClick = {
                        onEvent(PatientScreenEvent.OnBasdaiClick)
                    }
                )

                IndexCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    title = "ASDAS",
                    questionCount = 5,
                    onClick = {
                        onEvent(PatientScreenEvent.OnAsdasClick)
                    }
                )

                IndexCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    title = "BVAS",
                    questionCount = 9,
                    onClick = {
                        onEvent(PatientScreenEvent.OnBvasClick)
                    }
                )
            }
        }
    }
}

@Composable
private fun IndexCard(
    modifier: Modifier = Modifier,
    title: String,
    questionCount: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = { onClick() }
            )
            .background(color = colorResource(id = R.color.main_50))
            .padding(12.dp)
    ) {
        Text(text = "Индекс $title")
        Text(text = "Количество вопросов: $questionCount")
        TextButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                onClick()
            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = colorResource(id = R.color.icon_color)

            )
        ) {
            Text("Пройти")
        }
    }
}