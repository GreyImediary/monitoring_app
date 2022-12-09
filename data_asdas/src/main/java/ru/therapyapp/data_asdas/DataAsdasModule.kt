package ru.therapyapp.data_asdas

import ru.therapyapp.data_asdas.internal.AsdasIndexRepositoryImpl
import ru.therapyapp.data_asdas.internal.AsdasService
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit

class DataAsdasModule {
    val module = module {
        single<AsdasService> {
            get<Retrofit>().create(AsdasService::class.java)
        }

        single<AsdasRepository> {
            AsdasIndexRepositoryImpl(
                asdasService = get(),
                dispatcher = Dispatchers.IO,
            )
        }
    }
}