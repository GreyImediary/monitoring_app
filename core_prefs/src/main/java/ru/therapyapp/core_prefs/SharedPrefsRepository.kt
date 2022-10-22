package ru.therapyapp.core_prefs

interface SharedPrefsRepository {
    var isLoggedIn: Boolean
    var userId: Int
    var userType: String
    fun clearAll()
}