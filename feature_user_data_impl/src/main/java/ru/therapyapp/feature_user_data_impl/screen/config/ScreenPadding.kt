package ru.therapyapp.feature_user_data_impl.screen.config

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun getScreenHorizontalPadding(maxWidth: Dp) = if (maxWidth > 800.dp) {
    200.dp
} else if (maxWidth > 600.dp) {
    100.dp
} else {
    40.dp
}

fun getButtonsHorizontalPadding(maxWidth: Dp) = if (maxWidth > 800.dp) {
    150.dp
} else if (maxWidth > 600.dp) {
    50.dp
} else {
    0.dp
}