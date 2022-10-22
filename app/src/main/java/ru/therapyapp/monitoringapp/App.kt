package ru.therapyapp.monitoringapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.therapyapp.core_network.RetrofitModule
import ru.therapyapp.core_prefs.CorePrefsModule
import ru.therapyapp.data_auth.DataAuthModule
import ru.therapyapp.data_doctor.DataDoctorModule
import ru.therapyapp.data_patient.DataPatientModule
import ru.therapyapp.data_request.DataRequestModule
import ru.therapyapp.feature_auth_impl.AuthModule
import ru.therapyapp.feature_doctor_screen_impl.DoctorScreenModule
import ru.therapyapp.feature_patient_screen_impl.PatientScreenModule
import ru.therapyapp.feature_user_data_impl.screen.UserDataModule

class App : Application() {
    private val modules by lazy {
        listOf(
            RetrofitModule().module,
            DataAuthModule().module,
            AuthModule().module,
            UserDataModule().module,
            DataDoctorModule().module,
            DataPatientModule().module,
            DataRequestModule().module,
            PatientScreenModule().module,
            DoctorScreenModule().module,
            CorePrefsModule().module,
        )
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(modules)
        }
    }
}