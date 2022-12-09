package ru.therapyapp.feature_bvas_impl.screen

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.therapyapp.feature_bvas_impl.mvi.BvasSideEffect
import ru.therapyapp.feature_bvas_impl.mvi.BvasViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun BvasScreen(
    viewModel: BvasViewModel
) {
    val state = viewModel.collectAsState().value
    val activity = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = {
        handleSideEffect(activity, it)
    })

    BvasView(state = state, onEvent = { viewModel.dispatch(it) })
}

private fun handleSideEffect(activity: AppCompatActivity, effect: BvasSideEffect) {
    when (effect) {
        BvasSideEffect.Finish -> {
            activity.finish()
        }
        is BvasSideEffect.ShowToastMessage -> {
            Toast.makeText(activity, effect.text, Toast.LENGTH_SHORT).show()
        }
    }
}