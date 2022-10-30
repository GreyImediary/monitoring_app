package com.example.feature_asdas_impl.screen

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.feature_asdas_impl.mvi.AsdasSideEffect
import com.example.feature_asdas_impl.mvi.AsdasViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AsdasScreen(
    viewModel: AsdasViewModel
) {
    val state = viewModel.collectAsState().value
    val activity = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = {
        handleSideEffect(activity, it)
    })

    AsdasView(state = state, onEvent = { viewModel.dispatch(it) })
}

private fun handleSideEffect(activity: AppCompatActivity, effect: AsdasSideEffect) {
    when (effect) {
        AsdasSideEffect.Finish -> {
            activity.finish()
        }
        is AsdasSideEffect.ShowToastMessage -> {
            Toast.makeText(activity, effect.text, Toast.LENGTH_SHORT).show()
        }
    }
}