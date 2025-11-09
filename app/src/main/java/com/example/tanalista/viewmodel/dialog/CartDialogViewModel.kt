package com.example.tanalista.viewmodel.dialog

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanalista.repository.ProductCategoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class CartDialogViewModel(application: Application) : AndroidViewModel(application){

    private val productCategoryRepository = ProductCategoryRepository(application.applicationContext)

    val allCategories = productCategoryRepository.getAllCategories().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    var isDialogOpen by mutableStateOf(false)

    fun openDialog()
    {
        isDialogOpen = true
    }

    fun closeDialog()
    {
        isDialogOpen = false
    }
}