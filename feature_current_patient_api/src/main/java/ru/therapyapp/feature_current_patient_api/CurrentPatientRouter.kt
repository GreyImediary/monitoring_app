package ru.therapyapp.feature_current_patient_api

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_patient.api.entity.Patient

interface CurrentPatientRouter {
    fun openCurrentPatientScreen(activity: AppCompatActivity, patient: Patient)
}