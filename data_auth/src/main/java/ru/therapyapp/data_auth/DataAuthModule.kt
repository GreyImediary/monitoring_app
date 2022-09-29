package ru.therapyapp.data_auth

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_auth.api.AuthRepository
import ru.therapyapp.data_auth.internal.AuthRepositoryImpl
import ru.therapyapp.data_auth.internal.AuthService

class DataAuthModule {
    val module = module {
        single<AuthRepository> {
            AuthRepositoryImpl(get(), Dispatchers.IO)
        }
        single<AuthService> {
            get<Retrofit>().create(AuthService::class.java)
        }
    }
}