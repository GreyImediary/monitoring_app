package ru.therapyapp.data_sledai

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_sledai.internal.SelenaSledaiRepositoryImpl
import ru.therapyapp.data_sledai.internal.SelenaSledaiService

class SelenaSledaiModule {
    val module = module {
        single<SelenaSledaiService> {
            get<Retrofit>().create(SelenaSledaiService::class.java)
        }

        single<SelenaSledaiRepository> {
            SelenaSledaiRepositoryImpl(
                selenaSledaiService = get(),
                dispatcher = Dispatchers.IO,
            )
        }
    }
}