package com.example.feature_asdas_impl

import androidx.appcompat.app.AppCompatActivity
import com.example.feature_asdas_api.AsdasRouter

class AsdasRouterImpl : AsdasRouter {
    override fun openAsdasScreen(activity: AppCompatActivity, patientId: Int) {
        activity.startActivity(AsdasActivity.getIntent(activity, patientId))
    }
}