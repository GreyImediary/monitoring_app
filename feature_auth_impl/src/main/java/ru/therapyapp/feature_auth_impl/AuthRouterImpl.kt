package ru.therapyapp.feature_auth_impl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.feature_auth_api.AuthRouter

class AuthRouterImpl : AuthRouter {
    override fun showAuthScreen(activity: AppCompatActivity) {
        activity.startActivity(
            Intent(activity, AuthActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }
}