package ru.therapyapp.feature_sledai_impl

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.therapyapp.feature_sledai_api.SelenaSledaiRouter
import ru.therapyapp.feature_sledai_impl.mvi.SelenaSledaiViewModel

class SelenaSledaiScreenModule {
    val module = module {
        single<SelenaSledaiRouter> {
            SelenaSledaiRouterImpl()
        }

        viewModel { (patientId: Int) ->
            SelenaSledaiViewModel(patientId, sledaiRepository = get())
        }
    }
}