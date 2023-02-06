package ru.therapyapp.data_mkb

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_mkb.internal.MkbRepositoryImpl
import ru.therapyapp.data_mkb.internal.MkbService

class MkbModule {
    val module = module {
        single<MkbService> {
            get<Retrofit>().create(MkbService::class.java)
        }

        single<MkbRepository> {
            MkbRepositoryImpl(
                mkbService = get(),
                dispatcher = Dispatchers.IO,
            )
        }
    }
}