package ru.therapyapp.feature_auth_impl

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import ru.therapyapp.feature_auth_api.AuthRouter
import ru.therapyapp.feature_auth_impl.mvi.AuthViewModel


class AuthModule {
    val module = module {
        single<AuthRouter>{ AuthRouterImpl() }

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