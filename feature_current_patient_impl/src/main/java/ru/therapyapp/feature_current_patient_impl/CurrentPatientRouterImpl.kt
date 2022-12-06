package ru.therapyapp.feature_current_patient_impl

import androidx.appcompat.app.AppCompatActivity
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_current_patient_api.CurrentPatientRouter

class CurrentPatientRouterImpl : CurrentPatientRouter {
    override fun openCurrentPatientScreen(activity: AppCompatActivity, patient: Patient) {
        activity.startActivity(CurrentPatientActivity.getIntent(activity, patient))
    }
}