package com.example.tanalista.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.repository.ListRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val listRepository = ListRepository(application.applicationContext)

    var allLists by mutableStateOf(
        listRepository.getAllListsFromDatabase().asLiveData()
    )

    fun createANewList() : Unit
    {
        viewModelScope.launch {
            listRepository.createAList("")
        }
    }
}