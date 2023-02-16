package ru.therapyapp.feature_sledai_impl

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.feature_sledai_api.SelenaSledaiRouter

class SelenaSledaiRouterImpl : SelenaSledaiRouter {
    override fun openSelenaSledaiScreen(activity: AppCompatActivity, patientId: Int) {
        activity.startActivity(SelenaSledaiActivity.getIntent(activity, patientId))
    }
}