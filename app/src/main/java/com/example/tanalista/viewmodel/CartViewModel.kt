package com.example.tanalista.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.R
import com.example.tanalista.database.model.dto.ListItemDTO
import com.example.tanalista.enums.ProductCategory
import com.example.tanalista.repository.ProductListRepository
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val productListRepository = ProductListRepository(application.applicationContext)

    var allProductsInCurrentPage by mutableStateOf(
        productListRepository.getAllListProducts(1, false).asLiveData()
    )

    var isListToggleButtonChecked by mutableStateOf(true)
    var isCartToggleButtonChecked by mutableStateOf(false)
    var isAlphabeticalOrderSelected by mutableStateOf(false)
    var isCategoryOrderSelected by mutableStateOf(false)
    var isSortDropdownExpanded by mutableStateOf(false)
    var totalValue by mutableDoubleStateOf(0.0)

    init {
        updateTotalCartValue()
    }

    fun moveProductBetweenLists(listItem: ListItemDTO, isMovedToCart: Boolean) {

        viewModelScope.launch {
            if (isMovedToCart)
                productListRepository.addProductInCart(listItem)
            else
                productListRepository.removeProductInCart(listItem)
        }
    }

    fun showAllProductsInList() {
        viewModelScope.launch {
            allProductsInCurrentPage = productListRepository.getAllListProducts(1, false).asLiveData()
        }
        isCartToggleButtonChecked = false
        isListToggleButtonChecked = true
    }

    fun showAllProductsInCart() {
        viewModelScope.launch {
            allProductsInCurrentPage = productListRepository.getAllListProducts(1, true).asLiveData()
        }
        isCartToggleButtonChecked = true
        isListToggleButtonChecked = false
    }

    fun updateTotalCartValue() {
        viewModelScope.launch {
            productListRepository.getAllListProducts(1, true)
                .collect { products ->
                    totalValue = 0.0

                    products.forEach { product ->
                        totalValue += product.quantity * product.productPrice;
                    }
                }
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

    fun orderItemsBy(orderType: Int) {
        viewModelScope.launch {
             if (orderType == 0) {
                 allProductsInCurrentPage = productListRepository.getAllProductsFromListOrderByCategory(1, isCartToggleButtonChecked).asLiveData()
                 isCategoryOrderSelected = true
                 isAlphabeticalOrderSelected = false
            } else {
                 allProductsInCurrentPage = productListRepository.getAllProductsFromListOrderByAlphabetical(1, isCartToggleButtonChecked).asLiveData()
                 isCategoryOrderSelected = false
                 isAlphabeticalOrderSelected = true
            }
        }
    }
}