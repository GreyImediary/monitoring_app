package ru.therapyapp.feature_doctor_screen_impl

import org.koin.dsl.module
import ru.therapyapp.feature_doctor_screen_api.DoctorScreenRouter

class DoctorScreenModule {
    val module = module {
        single<DoctorScreenRouter> {
            DoctorScreenRouterImpl()
        }
    }
}