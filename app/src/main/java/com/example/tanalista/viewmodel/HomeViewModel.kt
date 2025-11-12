package com.example.tanalista.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.database.model.dto.ListItemDTO
import com.example.tanalista.repository.ProductListRepository
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private val productListRepository = ProductListRepository(application.applicationContext)

    val allProductsInCurrentList = productListRepository.getAllProductsFromList(1).asLiveData()

    fun moveProductBetweenLists(listItem: ListItemDTO, isMovedToCart: Boolean) {

        viewModelScope.launch {
            if (isMovedToCart)
                productListRepository.addProductInCart(listItem)
            else
                productListRepository.removeProductInCart(listItem)
        }
    }
}