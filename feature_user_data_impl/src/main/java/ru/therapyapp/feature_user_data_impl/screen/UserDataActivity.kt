package ru.therapyapp.feature_user_data_impl.screen

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import ru.therapyapp.data_auth.api.entity.User
import ru.therapyapp.feature_user_data_impl.screen.mvi.UserDataViewModel
import ru.therapyapp.feature_user_data_impl.screen.view.UserDataScreen

class UserDataActivity : AppCompatActivity(), KoinComponent {
    private val viewModel: UserDataViewModel by viewModel {
        parametersOf(intent.getParcelableExtra(KEY_USER, User::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            UserDataScreen(viewModel = viewModel)
        }
    }

    companion object {
        private const val KEY_USER = "KEY_USER"

        fun getIntent(activity: AppCompatActivity, user: User): Intent {
            return Intent(activity, UserDataActivity::class.java).apply {
                putExtra(KEY_USER, user)
            }
        }
    }
}