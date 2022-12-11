package ru.therapyapp.feature_answered_questionnaire_api

import androidx.appcompat.app.AppCompatActivity

interface QuestionnaireAnsweredScreenRouter {
    fun openQuestionnaireAnsweredScreen(appCompatActivity: AppCompatActivity, questionnaireId: Int)
}