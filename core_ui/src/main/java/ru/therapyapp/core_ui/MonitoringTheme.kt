package ru.therapyapp.core_ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.material3.MaterialTheme as Material3Theme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource

@Composable
fun MonitoringTheme(content: @Composable () -> Unit) {
    val primary = colorResource(R.color.main)
    val primaryDark = colorResource(R.color.main_dark)
    val secondary = colorResource(R.color.secondary)
    val text = colorResource(R.color.font_color)
    val white = colorResource(R.color.color_white)

    MaterialTheme(
        colors = lightColors(
            primary = primary,
            primaryVariant = primaryDark,
            secondary = secondary,
            secondaryVariant = secondary,
            onPrimary = text,
            onSecondary = text
        )
    ) {
        Material3Theme(
            colorScheme = lightColorScheme(
                primary = primary,
                onPrimary = text,
                secondary = secondary,
                onSecondary = text,
                secondaryContainer = primaryDark,
                onSecondaryContainer = white
            ),
            content = content
        )
    }
}
