package com.example.tanalista.screens.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.tanalista.model.repository.local.interfaces.ListRepository
import com.example.tanalista.screens.BaseViewModel
import com.example.tanalista.screens.home.converter.ListComponentConverter
import com.example.tanalista.screens.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val listRepository: ListRepository,
    private val listComponentConverter: ListComponentConverter
) : BaseViewModel(application) {

    private val _uiState = MutableStateFlow(HomeUiState.DEFAULT_STATE)

    val uiState: StateFlow<HomeUiState> = _uiState.onStart {
        initializeData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _uiState.value
    )

    fun initializeData() {
        viewModelScope.launch {
            listRepository.getAllListsFromDatabase().collect { lists ->
                _uiState.update {
                    it.copy(listState = listComponentConverter.convert(lists))
                }
            }
        }
    }
}