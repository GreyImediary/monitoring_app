@file:OptIn(ExperimentalMaterial3Api::class)

package ru.therapyapp.feature_current_patient_impl.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.datepicker.MaterialDatePicker
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.core_ui.AppButton
import ru.therapyapp.core_ui.R
import ru.therapyapp.core_ui.getPatientScreenHorizontalPadding
import ru.therapyapp.data_core.entity.Sex
import ru.therapyapp.data_core.utils.getStringDateFromLong
import ru.therapyapp.data_core.utils.getStringDateRepresentation
import ru.therapyapp.data_core.utils.getStringDateWithTimeFromLong
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_current_patient_impl.mvi.*
import java.util.*

@Composable
fun PatientScreen(
    viewModel: CurrentPatientViewModel,
    onEvent: (CurrentPatientEvent) -> Unit,
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = { handleSideEffects(context, it) })

    val localWidth = LocalConfiguration.current.screenWidthDp

    state.patient?.let {
        if (localWidth.dp < 600.dp) {
            PatientPhoneView(
                patient = it,
                state,
                context,
                onEvent = onEvent
            )
        } else {
            PatientView(
                patient = it,
                state,
                context,
                onEvent = onEvent
            )
        }

    }
}

private fun handleSideEffects(
    activity: AppCompatActivity,
    effect: CurrentPatientSideEffect,
) {
    when (effect) {
        CurrentPatientSideEffect.Finish -> {
            activity.finish()
        }
        is CurrentPatientSideEffect.ShowMessage -> {
            Toast.makeText(activity, effect.message, Toast.LENGTH_SHORT).show()
        }
    }
}




class MyXAxisFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return getStringDateFromLong(value.toLong())
    }
}