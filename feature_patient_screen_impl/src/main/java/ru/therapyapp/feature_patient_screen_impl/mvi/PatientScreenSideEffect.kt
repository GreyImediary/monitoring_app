package ru.therapyapp.feature_patient_screen_impl.mvi

sealed class PatientScreenSideEffect {
    data class ShowToast(val message: String) : PatientScreenSideEffect()
    object OpenBasdaiScreen : PatientScreenSideEffect()
    object OpenAsdasScreen : PatientScreenSideEffect()
    object OpenBvasScreen : PatientScreenSideEffect()
    object OpenSelenaSledaiScreen : PatientScreenSideEffect()
    data class OpenQuestionnaire(val questionnaireId: Int, val patientId: Int) : PatientScreenSideEffect()
    object ShowStartScreen : PatientScreenSideEffect()
    object Finish : PatientScreenSideEffect()
}
