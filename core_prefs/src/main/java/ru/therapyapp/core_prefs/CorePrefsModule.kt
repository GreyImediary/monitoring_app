package ru.therapyapp.core_prefs

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.therapyapp.core_prefs.internal.SharedPrefsRepositoryImpl

class CorePrefsModule {
    val module = module {
        single<SharedPrefsRepository> {
            SharedPrefsRepositoryImpl(androidContext())
        }
    }
}