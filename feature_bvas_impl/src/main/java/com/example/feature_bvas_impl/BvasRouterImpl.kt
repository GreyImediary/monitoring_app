package com.example.feature_bvas_impl

import androidx.appcompat.app.AppCompatActivity
import com.example.feature_bvas_api.BvasRouter

class BvasRouterImpl : BvasRouter {
    override fun openBvasScreen(activity: AppCompatActivity, patientId: Int) {
        activity.startActivity(BvasActivity.getIntent(activity, patientId))
    }
}