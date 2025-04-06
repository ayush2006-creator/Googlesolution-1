package com.example.sample_front_screen.ui.theme

import android.content.Context

class SessionManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_EMAIL = "user_email"
    }

    fun saveLoginState(isLoggedIn: Boolean, email: String? = null) {
        with(sharedPreferences.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            if (email != null) {
                putString(KEY_USER_EMAIL, email)
            }
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getSavedEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    fun clearSession() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}