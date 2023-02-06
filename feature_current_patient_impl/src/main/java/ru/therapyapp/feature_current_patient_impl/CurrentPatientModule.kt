package ru.therapyapp.feature_current_patient_impl

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.therapyapp.data_patient.api.entity.Patient
import ru.therapyapp.feature_current_patient_api.CurrentPatientRouter
import ru.therapyapp.feature_current_patient_impl.mvi.CurrentPatientViewModel

class CurrentPatientModule() {
    val module = module {
        single<CurrentPatientRouter> {
            CurrentPatientRouterImpl()
        }

        viewModel { (patient: Patient?, doctorId: Int) ->
            CurrentPatientViewModel(
                patient = patient,
                doctorId = doctorId,
                bvasRepository = get(),
                basdaiRepository = get(),
                asdasRepository = get(),
                commentRepository = get()
            )
        }
    }
}