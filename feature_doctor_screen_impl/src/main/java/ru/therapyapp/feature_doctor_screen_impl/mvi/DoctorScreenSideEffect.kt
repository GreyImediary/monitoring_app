package ru.therapyapp.feature_doctor_screen_impl.mvi

import ru.therapyapp.data_patient.api.entity.Patient

sealed class DoctorScreenSideEffect {
    data class ShowToast(val message: String) : DoctorScreenSideEffect()
    data class OpenPatientDataScreen(val patient: Patient, val doctorId: Int) : DoctorScreenSideEffect()
    data class OpenPatientAppScreen(val patient: Patient) : DoctorScreenSideEffect()
    data class OpenQuestionnaireAddScreen(val patients: List<Patient>, val doctorId: Int) : DoctorScreenSideEffect()
    data class OpenAnsweredQuestionnaireScreen(val questionnaireId: Int) : DoctorScreenSideEffect()
    object ShowAuthScreen : DoctorScreenSideEffect()
    data class OpenPatientCreateScreen(val doctorId: Int) : DoctorScreenSideEffect()
}