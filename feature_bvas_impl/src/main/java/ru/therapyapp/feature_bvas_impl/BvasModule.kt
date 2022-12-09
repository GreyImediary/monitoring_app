package ru.therapyapp.feature_bvas_impl

import ru.therapyapp.feature_bvas_api.BvasRouter
import ru.therapyapp.feature_bvas_impl.mvi.BvasViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class BvasModule {
    val module = module {
        single<BvasRouter> {
            BvasRouterImpl()
        }

        viewModel { (patientId: Int) ->
            BvasViewModel(patientId, bvasRepository = get())
        }
    }
}