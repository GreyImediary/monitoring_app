package ru.therapyapp.data_request

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_request.api.RequestRepository
import ru.therapyapp.data_request.internal.RequestRepositoryImpl
import ru.therapyapp.data_request.internal.RequestService

class DataRequestModule {
    val module = module {
        single<RequestRepository> {
            RequestRepositoryImpl(get(), Dispatchers.IO)
        }

        single<RequestService> {
            get<Retrofit>().create(RequestService::class.java)
        }
    }
}