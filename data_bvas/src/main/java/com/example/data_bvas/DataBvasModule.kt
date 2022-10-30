package com.example.data_bvas

import com.example.data_bvas.internal.BvasIndexRepositoryImpl
import com.example.data_bvas.internal.BvasService
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit

class DataBvasModule {
    val module = module {
        single<BvasService> {
            get<Retrofit>().create(BvasService::class.java)
        }

        single<BvasRepository> {
            BvasIndexRepositoryImpl(
                bvasService = get(),
                dispatcher = Dispatchers.IO,
            )
        }
    }
}