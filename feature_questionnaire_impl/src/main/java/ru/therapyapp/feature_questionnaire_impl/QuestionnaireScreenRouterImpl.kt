package ru.therapyapp.feature_questionnaire_impl

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.feature_questionnaire_api.QuestionnaireScreenRouter

class QuestionnaireScreenRouterImpl : QuestionnaireScreenRouter {

    override fun openQuestionnaireScreen(
        appCompatActivity: AppCompatActivity,
        patientId: Int,
        questionnaireId: Int,
    ) {
        appCompatActivity.startActivity(
            QuestionnaireActivity.getIntent(
                appCompatActivity,
                patientId,
                questionnaireId
            )
        )
    }
}