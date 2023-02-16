package ru.therapyapp.feature_sledai_impl.screen

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.therapyapp.feature_sledai_impl.mvi.SelenaSledaiSideEffect
import ru.therapyapp.feature_sledai_impl.mvi.SelenaSledaiViewModel

@Composable
fun SelenaSledaiScreen(
    viewModel: SelenaSledaiViewModel
) {
    val state = viewModel.collectAsState().value
    val activity = LocalContext.current as AppCompatActivity

    viewModel.collectSideEffect(sideEffect = {
        handleSideEffect(activity, it)
    })

    SelenaSledaiView(state = state, onEvent = { viewModel.dispatch(it) })
}

private fun handleSideEffect(activity: AppCompatActivity, effect: SelenaSledaiSideEffect) {
    when (effect) {
        SelenaSledaiSideEffect.Finish -> {
            activity.finish()
        }
        is SelenaSledaiSideEffect.ShowToastMessage -> {
            Toast.makeText(activity, effect.text, Toast.LENGTH_SHORT).show()
        }
    }
}