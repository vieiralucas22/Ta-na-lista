package com.example.tanalista.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.R
import com.example.tanalista.database.model.dto.ListItemDTO
import com.example.tanalista.enums.ProductCategory
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

    fun getCategoryIcon(category: String): Int {

        val enumCategory = ProductCategory.entries
            .firstOrNull { it.name.equals(category, ignoreCase = true) }

        return when (enumCategory) {
            ProductCategory.Food -> R.drawable.ic_food
            ProductCategory.Drink -> R.drawable.ic_drink
            ProductCategory.Clean -> R.drawable.ic_clean
            ProductCategory.Barbecue -> R.drawable.ic_barbecue
            ProductCategory.Utilities -> R.drawable.ic_utilities
            ProductCategory.Toilet -> R.drawable.ic_toilet
            else -> {
                R.drawable.ic_undefined
            }
        }
    }
}