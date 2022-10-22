package ru.therapyapp.data_basdai

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_basdai.internal.BasdaiRepositoryImpl
import ru.therapyapp.data_basdai.internal.BasdaiService

class DataBasdaiModule {
    val module = module {
        single<BasdaiService> {
            get<Retrofit>().create(BasdaiService::class.java)
        }

        single<BasdaiRepository> {
            BasdaiRepositoryImpl(
                basdaiService = get(),
                dispatcher = Dispatchers.IO,
            )
        }
    }
}