package com.falabella.falabellatest.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falabella.falabellatest.util.Constants.KEY
import com.falabella.falabellatest.util.EncryptionHelper
import com.falabella.falabellatest.util.SharedPreferencesHelper

class LoginViewModel(context: Context) : ViewModel() {

    var login = MutableLiveData<Boolean>()
    private val prefs = SharedPreferencesHelper.invoke(context)
    private val encrypt = EncryptionHelper()

    fun validateLogin(user : String, pass : String)  {
        val userE = encrypt.encrypt(user, KEY)
        val passE = encrypt.encrypt(pass, KEY)
        login.value = prefs.getUser() == userE && prefs.getPassword() == passE
    }


}