package com.example.tanalista.viewmodel.dialog

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanalista.database.model.dto.ListItemDTO
import com.example.tanalista.repository.ProductListRepository
import kotlinx.coroutines.launch

class DeleteListItemDialogViewModel(application: Application) : AndroidViewModel(application) {

    private val productListRepository = ProductListRepository(application.applicationContext)

    private lateinit var currentProduct : ListItemDTO

    var isDialogOpen by mutableStateOf(false)

    fun openDialog(listItemDTO : ListItemDTO) {
        isDialogOpen = true
        currentProduct = listItemDTO
    }

    fun closeDialog() {
        isDialogOpen = false
    }

    fun deleteListItem()
    {
        viewModelScope.launch {
            productListRepository.deleteProductFromList(currentProduct)
        }
        closeDialog()
    }
}