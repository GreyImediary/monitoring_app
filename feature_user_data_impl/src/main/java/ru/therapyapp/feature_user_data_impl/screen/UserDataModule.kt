package ru.therapyapp.feature_user_data_impl.screen

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.therapyapp.data_core.entity.User
import ru.therapyapp.feature_user_data_api.UserDataRouter
import ru.therapyapp.feature_user_data_impl.screen.mvi.UserDataViewModel

class UserDataModule {
    val module = module {
        viewModel { (user: User?) ->
            UserDataViewModel(
                user = user,
                patientRepository = get(),
                doctorRepository = get(),
                prefsRepository = get(),
            )
        }

        single<UserDataRouter> { UserDataRouterImpl() }
    }
}