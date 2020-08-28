package com.falabella.falabellatest.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import com.falabella.falabellatest.BuildConfig
import com.falabella.falabellatest.util.Constants.PASS
import com.falabella.falabellatest.util.Constants.USER
import com.falabella.falabellatest.util.EncryptionHelper
import com.falabella.falabellatest.util.SharedPreferencesHelper

class MainViewModel(val context: Context) : ViewModel() {

    private val prefsHelper = SharedPreferencesHelper.invoke(context)
    private val encrypt = EncryptionHelper()

    fun saveUserData(){
        prefsHelper.saveLogin(encrypt.encrypt(USER, BuildConfig.ACCESS_TOKEN), encrypt.encrypt(PASS, BuildConfig.ACCESS_TOKEN))
    }

}