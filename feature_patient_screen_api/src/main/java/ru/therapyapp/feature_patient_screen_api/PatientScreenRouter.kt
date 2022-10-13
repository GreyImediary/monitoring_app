package ru.therapyapp.feature_patient_screen_api

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_patient.api.entity.Patient

interface PatientScreenRouter {
    fun openPatientScreen(activity: AppCompatActivity, patient: Patient)
}