package ru.therapyapp.feature_basdai_impl

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.feature_basdai_api.BasdaiRouter

class BasdaiRouterImpl : BasdaiRouter {
    override fun openBasdaiScreen(activity: AppCompatActivity, patientId: Int) {
        activity.startActivity(BasdaiActivity.getIntent(activity, patientId))
    }
}