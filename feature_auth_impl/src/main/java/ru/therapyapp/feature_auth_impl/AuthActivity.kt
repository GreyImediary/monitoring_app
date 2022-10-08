package ru.therapyapp.feature_auth_impl

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import ru.therapyapp.feature_auth_impl.mvi.AuthViewModel
import ru.therapyapp.feature_auth_impl.screen.views.AuthScreen

class AuthActivity : AppCompatActivity(), KoinComponent {
    private val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            AuthScreen(
                viewModel = viewModel,
                onEvent = {
                    viewModel.dispatch(it)
                }
            )
        }
    }
}