package com.appbase.data.preference

import android.app.Application
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val application: Application
) {
    private val isLoggedIn = "isLoggedIn"
    private val tokenPrefsKey = "bearer_token"

    var jwtToken: String? = getToken()
        private set
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(isLoggedIn, false)
    }

    fun saveToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(tokenPrefsKey, token)
            putBoolean(isLoggedIn, true)
            jwtToken = token
            apply()
        }
    }

    private fun getToken(): String {
        return sharedPreferences.getString(tokenPrefsKey, "") ?: ""
    }
}