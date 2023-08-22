package com.example.traveler

import android.content.Context
import android.content.SharedPreferences
object TokenManager {
    private const val PREF_NAME = "TokenPrefs"
    private const val KEY_TOKEN = "token"

    private lateinit var preferences: SharedPreferences

    fun initialize(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        preferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return preferences.getString(KEY_TOKEN, null)
    }
}
