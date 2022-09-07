package com.icerrate.vama.myalbums.provider.preference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppPreferences @Inject constructor(
    val context: Context
) {
    companion object {
        const val PREFS_NAME = "settings_preferences"
        const val COPYRIGHT_KEY = "_copyright_key"
    }

    private var sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun saveCopyrightText(copyright: String) {
        put(COPYRIGHT_KEY, copyright)
    }

    fun getCopyrightText(): String? {
        return getString(COPYRIGHT_KEY, "")
    }

    fun clearAll() {
        editor.clear()
            .apply()
    }

    private fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    private fun getString(key: String, default: String? = null): String? {
        return sharedPref.getString(key, default)
    }
}