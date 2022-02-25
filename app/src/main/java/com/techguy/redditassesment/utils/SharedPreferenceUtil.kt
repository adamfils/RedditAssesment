package com.techguy.redditassesment.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SharedPreferenceUtil {

    const val CACHE_PREF = "cache_pref"
    const val CHARACTER_KEY = "character_key"


    private fun Context.getSecureSharedPreference(name: String = CACHE_PREF): SharedPreferences {
        //return context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val masterKeyAlias =
            MasterKey.Builder(this).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return EncryptedSharedPreferences.create(
            this,
            name,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun Context.getPrefString(key: String): String {
        return getSecureSharedPreference().getString(key, "") ?: ""
    }

    fun Context.setPrefString(key: String, value: String = "") {
        if (value.isEmpty()) return
        val editor = getSecureSharedPreference().edit()
        editor.putString(key, value)
        editor.apply()
    }
}