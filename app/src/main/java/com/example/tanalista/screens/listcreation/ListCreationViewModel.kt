package com.example.tanalista.screens.listcreation

import android.app.Application
import com.example.tanalista.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListCreationViewModel @Inject constructor(
    application: Application,
) : BaseViewModel(application)
{

}