package com.example.tanalista.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    fun getText(stringId : Int) : String
    {
        return application.getText(stringId).toString()
    }

}