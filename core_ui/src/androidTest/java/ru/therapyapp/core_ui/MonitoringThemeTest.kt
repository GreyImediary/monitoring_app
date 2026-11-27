package ru.therapyapp.core_ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.MaterialTheme as Material3Theme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MonitoringThemeTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun bothMaterialThemesUseSharedPalette() {
        lateinit var expected: List<Color>
        lateinit var actual: List<Color>
        compose.setContent {
            MonitoringTheme {
                val primary = colorResource(R.color.main)
                val secondary = colorResource(R.color.secondary)
                val selection = colorResource(R.color.main_dark)
                val text = colorResource(R.color.font_color)
                val white = colorResource(R.color.color_white)
                val material2 = MaterialTheme.colors
                val material3 = Material3Theme.colorScheme
                SideEffect {
                    expected = listOf(primary, secondary, text, text, primary, secondary, selection, white)
                    actual = listOf(
                        material2.primary, material2.secondary, material2.onPrimary, material2.onSecondary,
                        material3.primary, material3.secondary, material3.secondaryContainer,
                        material3.onSecondaryContainer
                    )
                }
            }
        }
        compose.runOnIdle { assertEquals(expected, actual) }
    }
}
