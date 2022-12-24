package ru.therapyapp.core_ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

fun getMediumHorizontalPadding(maxWidth: Dp) = if (maxWidth > 800.dp) {
    80.dp
} else if (maxWidth > 600.dp) {
    50.dp
} else {
    16.dp
}

fun getPatientScreenHorizontalPadding(maxWidth: Dp) = if (maxWidth > 800.dp) {
    58.dp
} else if (maxWidth > 600.dp) {
    38.dp
} else {
    12.dp
}

fun getLargeHorizontalPadding(maxWidth: Dp) = if (maxWidth > 800.dp) {
    120.dp
} else if (maxWidth > 600.dp) {
    80.dp
} else {
    20.dp
}

fun getCellCountForGrid(maxWidth: Dp) = if (maxWidth < 600.dp) 1 else 2

fun getCellCountForQuestionnairesGrid(maxWidth: Dp) = if (maxWidth < 600.dp) 1 else 3