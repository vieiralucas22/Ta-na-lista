package com.example.tanalista.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.repository.local.interfaces.IListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (application: Application, val listRepository : IListRepository) : AndroidViewModel(application) {

    var allLists by mutableStateOf(
        listRepository.getAllListsFromDatabase().asLiveData()
    )

    fun createANewList()
    {
        viewModelScope.launch {
            listRepository.insertList("")
        }
    }
}