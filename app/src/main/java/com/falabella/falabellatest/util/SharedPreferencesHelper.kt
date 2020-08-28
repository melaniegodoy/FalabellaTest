package com.falabella.falabellatest.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.falabella.falabellatest.util.Constants.PASS
import com.falabella.falabellatest.util.Constants.PASSWORD
import com.falabella.falabellatest.util.Constants.USER
import com.falabella.falabellatest.util.Constants.USERNAME

class SharedPreferencesHelper {

    companion object{
        private var prefs : SharedPreferences?= null
        @Volatile private var instance : SharedPreferencesHelper ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) : SharedPreferencesHelper = instance ?: synchronized(LOCK){
            instance ?: buildHelper(context).also{
                instance = it
            }
        }

        private fun buildHelper(context: Context) : SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveLogin(user : String?, pass : String?){
        prefs?.edit(commit = true){putString(USERNAME, user)}
        prefs?.edit(commit = true){putString(PASSWORD, pass)}
    }

    fun getUser() = prefs?.getString(USERNAME, "default")
    fun getPassword() = prefs?.getString(PASSWORD, "default")

}