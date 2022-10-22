package ru.therapyapp.feature_basdai_impl.screen

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.core_android.MviViewModel
import ru.therapyapp.feature_basdai_impl.mvi.BasdaiSideEffect
import ru.therapyapp.feature_basdai_impl.mvi.BasdaiViewModel

@Composable
fun BasdaiScreen(
    viewModel: BasdaiViewModel
) {
    val state = viewModel.collectAsState().value
    val activity = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = {
        handleSideEffect(activity, it)
    })

    BasdaiView(state = state, onEvent = { viewModel.dispatch(it) })
}

private fun handleSideEffect(activity: AppCompatActivity, effect: BasdaiSideEffect) {
    when (effect) {
        BasdaiSideEffect.Finish -> {
            activity.finish()
        }
        is BasdaiSideEffect.ShowToastMessage -> {
            Toast.makeText(activity, effect.text, Toast.LENGTH_SHORT).show()
        }
    }
}