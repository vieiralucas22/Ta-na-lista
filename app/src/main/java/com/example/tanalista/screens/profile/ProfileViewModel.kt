package com.example.tanalista.screens.profile

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    var imageUri by mutableStateOf<Uri?>(null)

    fun updateImage(uri: Uri?) {
        imageUri = uri
    }
}