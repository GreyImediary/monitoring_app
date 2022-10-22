package ru.therapyapp.feature_basdai_impl

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.therapyapp.feature_basdai_api.BasdaiRouter
import ru.therapyapp.feature_basdai_impl.mvi.BasdaiViewModel

class BasdaiModule {
    val module = module {
        single<BasdaiRouter> {
            BasdaiRouterImpl()
        }

        viewModel { (patientId: Int) ->
            BasdaiViewModel(patientId, basdaiRepository = get())
        }
    }
}