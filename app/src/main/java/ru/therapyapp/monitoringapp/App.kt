package ru.therapyapp.monitoringapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.therapyapp.core_network.RetrofitModule
import ru.therapyapp.data_auth.DataAuthModule
import ru.therapyapp.feature_auth_impl.AuthModule

class App : Application() {
    private val modules by lazy {
        listOf(
            RetrofitModule().module,
            DataAuthModule().module,
            AuthModule().module,
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