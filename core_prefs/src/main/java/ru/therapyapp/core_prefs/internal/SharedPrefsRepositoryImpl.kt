package ru.therapyapp.core_prefs.internal

import android.content.Context
import android.content.SharedPreferences
import ru.therapyapp.core_prefs.SharedPrefsRepository

internal class SharedPrefsRepositoryImpl(
    val context: Context,
) : SharedPrefsRepository {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun clearAll() {
        prefs.edit().clear().apply()
    }

    override var isLoggedIn: Boolean
        get() = prefs.getBoolean(PREF_IS_LOGGED, false)
        set(value) {
            prefs.edit().putBoolean(PREF_IS_LOGGED, value).apply()
        }

    override var userId: Int
        get() = prefs.getInt(PREF_IS_USER_ID, -1)
        set(value) {
            prefs.edit().putInt(PREF_IS_USER_ID, value).apply()
        }

    override var userType: String
        get() = prefs.getString(PREF_IS_USER_TYPE, "") ?: ""
        set(value) {
            prefs.edit().putString(PREF_IS_USER_TYPE, value).apply()
        }

    private companion object {
        const val PREFS_NAME = "monitoringAppSharedPref"
        const val PREF_IS_LOGGED = "PREF_IS_LOGGED"
        const val PREF_IS_USER_ID = "PREF_IS_USER_ID"
        const val PREF_IS_USER_TYPE = "PREF_IS_USER_TYPE"
    }
}