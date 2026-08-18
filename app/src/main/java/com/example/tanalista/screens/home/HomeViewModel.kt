package com.example.tanalista.screens.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.tanalista.R
import com.example.tanalista.dsm.bottomsheet.BottomSheetButtonAction
import com.example.tanalista.dsm.bottomsheet.model.BottomSheetButtonState
import com.example.tanalista.dsm.bottomsheet.model.BottomSheetState
import com.example.tanalista.model.repository.local.interfaces.ListRepository
import com.example.tanalista.screens.BaseViewModel
import com.example.tanalista.screens.home.action.DeleteAction
import com.example.tanalista.screens.home.action.EditAction
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
                    it.copy(
                        listState = listComponentConverter.convert(lists),
                        bottomSheetState = BottomSheetState(
                            isVisible = false,
                            buttonList = emptyList()
                        )
                    )
                }
            }
        }
    }

    private fun getBottomSheetButtons(listId: Long): List<BottomSheetButtonState> {
        return listOf(
            BottomSheetButtonState(
                titleId = R.string.edit,
                iconId = R.drawable.ic_edit,
                action = EditAction(listId)
            ),
            BottomSheetButtonState(
                titleId = R.string.delete,
                iconId = R.drawable.ic_trash,
                action = DeleteAction(listId)
            )
        )
    }

    fun dismissBottomSheet() {
        _uiState.update {
            it.copy(bottomSheetState = it.bottomSheetState.copy(isVisible = false))
        }
    }

    fun showBottomSheet(listId: Long) {
        _uiState.update {
            it.copy(
                bottomSheetState = it.bottomSheetState.copy(
                    isVisible = true,
                    buttonList = getBottomSheetButtons(listId)
                )
            )
        }
    }

    fun deleteList(listId: Long) {
        viewModelScope.launch {
            listRepository.deleteList(listId)
        }
    }
}