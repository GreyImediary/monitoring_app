package ru.therapyapp.feature_asdas_impl

import ru.therapyapp.feature_asdas_api.AsdasRouter
import ru.therapyapp.feature_asdas_impl.mvi.AsdasViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AsdasModule {
    val module = module {
        single<AsdasRouter> {
            AsdasRouterImpl()
        }

        viewModel { (patientId: Int) ->
            AsdasViewModel(patientId, asdasRepository = get())
        }
    }
}