package ru.therapyapp.monitoringapp

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.R as MaterialR
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import ru.therapyapp.core_ui.R as PaletteR

@RunWith(AndroidJUnit4::class)
class PaletteInstrumentedTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun mergedPaletteUsesNewColorsAndPreservesAlpha() {
        val expected = mapOf(
            PaletteR.color.main to 0xFFF98C84.toInt(),
            PaletteR.color.main_dark to 0xFFA25A55.toInt(),
            PaletteR.color.main_50 to 0x50F98C84,
            PaletteR.color.secondary to 0xFFC6BB92.toInt(),
            PaletteR.color.secondary_25 to 0x25C6BB92,
            PaletteR.color.icon_color to 0xFF313131.toInt(),
            PaletteR.color.font_color to 0xFF1E1E1E.toInt()
        )
        for ((resource, color) in expected) {
            assertEquals(context.resources.getResourceEntryName(resource), color, ContextCompat.getColor(context, resource))
        }
    }

    @Test
    fun androidThemeUsesSharedPalette() {
        val themed = ContextThemeWrapper(context, R.style.MonitoringAppTheme)
        assertThemeColor(themed, MaterialR.attr.colorPrimary, PaletteR.color.main)
        assertThemeColor(themed, MaterialR.attr.colorSecondary, PaletteR.color.secondary)
        assertThemeColor(themed, androidx.appcompat.R.attr.colorAccent, PaletteR.color.icon_color)
    }

    private fun assertThemeColor(context: Context, attribute: Int, resource: Int) {
        val values = context.obtainStyledAttributes(intArrayOf(attribute))
        try {
            assertEquals(ContextCompat.getColor(context, resource), values.getColor(0, 0))
        } finally {
            values.recycle()
        }
    }
}
