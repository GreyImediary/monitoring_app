package ru.therapyapp.feature_patient_screen_impl

import org.koin.dsl.module
import ru.therapyapp.feature_patient_screen_api.PatientScreenRouter

class PatientScreenModule {
    val module = module {
        single<PatientScreenRouter> {
            PatientScreenRouterImpl()
        }
    }
}