package ru.therapyapp.feature_user_data_impl.screen

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_core.entity.User
import ru.therapyapp.feature_user_data_api.UserDataRouter

class UserDataRouterImpl : UserDataRouter {
    override fun openUserDataScreen(activity: AppCompatActivity, user: User) {
        activity.startActivity(
            UserDataActivity.getIntent(activity, user)
        )
    }
}