package com.example.tanalista.ui.viewmodel.dialog

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.repository.local.interfaces.IProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteListItemDialogViewModel @Inject constructor(
    application: Application,
    val productListRepository: IProductListRepository
) : AndroidViewModel(application) {

    private lateinit var currentProduct: ListItemDTO

    var isDialogOpen by mutableStateOf(false)

    fun openDialog(listItemDTO: ListItemDTO) {
        isDialogOpen = true
        currentProduct = listItemDTO
    }

    fun closeDialog() {
        isDialogOpen = false
    }

    fun deleteListItem() {
        viewModelScope.launch {
            productListRepository.deleteProductFromList(currentProduct)
        }
        closeDialog()
    }
}