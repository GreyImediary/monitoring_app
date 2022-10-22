package ru.therapyapp.feature_auth_impl

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import ru.therapyapp.feature_auth_impl.mvi.AuthViewModel


class AuthModule {
    val module = module {
        viewModel {
            AuthViewModel(
                authRepository = get(),
                doctorRepository = get(),
                patientRepository = get(),
                prefsRepository = get(),
            )
        }
    }
}