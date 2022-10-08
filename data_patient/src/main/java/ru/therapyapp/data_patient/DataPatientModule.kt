package ru.therapyapp.data_patient

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.therapyapp.data_patient.api.PatientRepository
import ru.therapyapp.data_patient.internal.PatientRepositoryImpl
import ru.therapyapp.data_patient.internal.PatientService

class DataPatientModule {
    val module = module {
        single<PatientRepository> {
            PatientRepositoryImpl(
                patientService = get(),
                dispatcher = Dispatchers.IO
            )
        }

        single<PatientService> {
            get<Retrofit>().create(PatientService::class.java)
        }
    }
}