package ru.therapyapp.feature_questionnaire_add_api

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_patient.api.entity.Patient

interface QuestionnaireAddScreenRouter {
    fun openQuestionnaireAddScreen(appCompatActivity: AppCompatActivity, doctorId: Int, patients: List<Patient>)
}