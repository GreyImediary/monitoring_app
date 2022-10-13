package ru.therapyapp.feature_patient_screen_impl

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_patient_screen_api.PatientScreenRouter

class PatientScreenRouterImpl : PatientScreenRouter {
    override fun openPatientScreen(activity: AppCompatActivity, patient: Patient) {
        activity.startActivity(PatientScreenActivity.getIntent(activity, patient))
    }
}