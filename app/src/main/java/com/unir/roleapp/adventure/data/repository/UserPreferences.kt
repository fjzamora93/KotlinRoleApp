package com.unir.roleapp.adventure.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("rolapp_prefs", Context.MODE_PRIVATE)

    fun saveEmail(email: String) {
        prefs.edit().putString("user_email", email).apply()
    }

    fun getEmail(): String? {
        return prefs.getString("user_email", null)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
