package com.example.tanalista.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.R
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.enums.ProductCategory
import com.example.tanalista.repository.local.interfaces.IProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    application: Application,
    val productListRepository: IProductListRepository
) : AndroidViewModel(application) {

    var _isCartSelected = MutableStateFlow(false)

    var isListToggleButtonChecked by mutableStateOf(true)
    var isCartToggleButtonChecked by mutableStateOf(false)

    var totalValue by mutableDoubleStateOf(0.0)

    var allProductsInCurrentPage = _isCartSelected
        .flatMapLatest {
                isCart ->
            productListRepository.getAllListProducts(1, isCart)
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        ).asLiveData()

    init {
        updateTotalCartValue()
    }

    fun moveProductBetweenLists(listItem: ListItemDTO, isMovedToCart: Boolean) {

        viewModelScope.launch {
            if (isMovedToCart)
                productListRepository.addProductInCart(listItem)
            else
                productListRepository.deleteProductInCart(listItem)
        }
    }

    fun showAllProductsInList() {
        _isCartSelected.value = false
        isCartToggleButtonChecked = false
        isListToggleButtonChecked = true
    }

    fun showAllProductsInCart() {
        _isCartSelected.value = true
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

}