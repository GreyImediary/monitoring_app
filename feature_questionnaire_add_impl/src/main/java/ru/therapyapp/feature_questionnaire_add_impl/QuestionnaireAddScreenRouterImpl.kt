package ru.therapyapp.feature_questionnaire_add_impl

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_questionnaire_add_api.QuestionnaireAddScreenRouter

class QuestionnaireAddScreenRouterImpl : QuestionnaireAddScreenRouter {
    override fun openQuestionnaireAddScreen(
        appCompatActivity: AppCompatActivity,
        doctorId: Int,
        patients: List<Patient>,
    ) {
        appCompatActivity.startActivity(
            QuestionnaireAddActivity.getIntent(
                appCompatActivity,
                doctorId,
                patients
            )
        )
    }
}