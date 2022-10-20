package ru.therapyapp.feature_doctor_screen_impl

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.feature_doctor_screen_api.DoctorScreenRouter
import ru.therapyapp.feature_doctor_screen_impl.mvi.DoctorScreenViewModel

class DoctorScreenModule {
    val module = module {
        single<DoctorScreenRouter> {
            DoctorScreenRouterImpl()
        }

        viewModel { (doctor: Doctor?) -> DoctorScreenViewModel(doctor, get(), get())}
    }
}