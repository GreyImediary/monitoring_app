package ru.therapyapp.feature_questionnaire_api

import androidx.appcompat.app.AppCompatActivity

interface QuestionnaireScreenRouter {
    fun openQuestionnaireScreen(appCompatActivity: AppCompatActivity, patientId: Int, questionnaireId: Int)
}