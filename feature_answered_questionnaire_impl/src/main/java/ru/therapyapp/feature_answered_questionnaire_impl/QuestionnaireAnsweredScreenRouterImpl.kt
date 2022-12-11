package ru.therapyapp.feature_answered_questionnaire_impl

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.feature_answered_questionnaire_api.QuestionnaireAnsweredScreenRouter

class QuestionnaireAnsweredScreenRouterImpl : QuestionnaireAnsweredScreenRouter {
    override fun openQuestionnaireAnsweredScreen(
        appCompatActivity: AppCompatActivity,
        questionnaireId: Int,
    ) {
        appCompatActivity.startActivity(
            QuestionnaireAnsweredActivity.getIntent(
                appCompatActivity,
                questionnaireId
            )
        )
    }
}