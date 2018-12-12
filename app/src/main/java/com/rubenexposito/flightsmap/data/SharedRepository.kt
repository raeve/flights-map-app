package com.rubenexposito.flightsmap.data

import android.content.Context
import android.content.SharedPreferences
import java.util.*

interface SharedRepository {
    fun saveToken(token: String, tokenType: String, expiresIn: Long)
    fun expiredToken(): Boolean
    fun getToken(): String
}

class SharedRepositoryImpl(private val context: Context) : SharedRepository {
    override fun saveToken(token: String, tokenType: String, expiresIn: Long) {
        with(getPreferences().edit()) {
            putString(KEY_TOKEN, token)
            putString(KEY_TOKEN_TYPE, tokenType)
            putLong(KEY_EXPIRES_IN, Calendar.getInstance().timeInMillis + (expiresIn * 1000))
            apply()
        }
    }

    override fun expiredToken(): Boolean = getPreferences().getLong(KEY_EXPIRES_IN, 0) < Calendar.getInstance().timeInMillis

    override fun getToken(): String = getPreferences().getString(KEY_TOKEN, "")!!

    private fun getPreferences(): SharedPreferences = context.getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE)

    companion object {
        const val SHARED_PACKAGE = "sharedPackage:lufthansa"
        const val KEY_TOKEN = "key:token"
        const val KEY_TOKEN_TYPE = "key:tokenType"
        const val KEY_EXPIRES_IN = "key:expiresIn"
    }
}