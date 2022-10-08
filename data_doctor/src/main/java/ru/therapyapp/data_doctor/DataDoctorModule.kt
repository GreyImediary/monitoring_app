package ru.therapyapp.data_doctor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_doctor.api.DoctorRepository
import ru.therapyapp.data_doctor.internal.DoctorRepositoryImpl
import ru.therapyapp.data_doctor.internal.DoctorService

class DataDoctorModule {
    val module = module {
        single<DoctorRepository> {
            DoctorRepositoryImpl(
                doctorService = get(),
                dispatcher = Dispatchers.IO
            )
        }

        single<DoctorService> {
            get<Retrofit>().create(DoctorService::class.java)
        }
    }
}