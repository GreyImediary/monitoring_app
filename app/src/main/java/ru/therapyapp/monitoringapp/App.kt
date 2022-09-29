package ru.therapyapp.monitoringapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.therapyapp.core_network.RetrofitModule

class App : Application() {
    private val modules by lazy {
        listOf(
            RetrofitModule().module
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