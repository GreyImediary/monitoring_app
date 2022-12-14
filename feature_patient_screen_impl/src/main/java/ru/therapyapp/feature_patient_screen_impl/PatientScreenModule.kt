package ru.therapyapp.feature_patient_screen_impl

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.therapyapp.data_doctor.api.entity.Doctor
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_patient_screen_api.PatientScreenRouter
import ru.therapyapp.feature_patient_screen_impl.mvi.PatientScreenViewModel

class PatientScreenModule {
    val module = module {
        single<PatientScreenRouter> {
            PatientScreenRouterImpl()
        }

        viewModel { (patient: Patient?) -> PatientScreenViewModel(patient, get(), get(), get())}

    }
}