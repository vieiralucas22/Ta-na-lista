package com.example.tanalista.screens.cart

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.model.repository.local.interfaces.ProductListRepository
import com.example.tanalista.screens.BaseViewModel
import com.example.tanalista.screens.cart.model.CartScreenState
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
class CartScreenViewModel @Inject constructor(
    application: Application,
    val productListRepository: ProductListRepository
) : BaseViewModel(application) {

    private val _uiState = MutableStateFlow(CartScreenState.DEFAULT_STATE)

    val uiState: StateFlow<CartScreenState> =
        _uiState.onStart { 
            initializeScreenData()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _uiState.value
        )

    private fun initializeScreenData() {
        viewModelScope.launch {
            val allProducts = productListRepository.getAllListProducts(1)

            _uiState.update { it.copy(allProductsInList = allProducts) }
            
            updateTotalCartValue()
        }
    }

    fun moveProductBetweenLists(listItem: ListItemDTO, isAddedToCart: Boolean) {
        viewModelScope.launch {
            if (isAddedToCart)
                productListRepository.addProductToCart(listItem)
            else
                productListRepository.removeProductFromCart(listItem)
        }
    }

    fun showAllProductsInList() {
        viewModelScope.launch {
            val allProducts = productListRepository.getAllListProducts(1, false)

            _uiState.update {
                it.copy(
                    cartToggle = it.cartToggle.copy(isChecked = false),
                    listToggle = it.listToggle.copy(isChecked = true),
                    allProductsInList = allProducts
                )
            }
        }
    }

    fun showAllProductsInCart() {
        viewModelScope.launch {
            val allProducts = productListRepository.getAllListProducts(1, true)

            _uiState.update {
                it.copy(
                    cartToggle = it.cartToggle.copy(isChecked = true),
                    listToggle = it.listToggle.copy(isChecked = false),
                    allProductsInList = allProducts
                )
            }
        }
    }

    suspend fun updateTotalCartValue() {
        productListRepository.getAllListProducts(1, true)
            .collect { products ->
                var total = 0.0
                products.forEach { product ->
                    total += product.quantity * product.productPrice
                }
                _uiState.update {
                    it.copy(totalCartValue = total)
                }
            }
    }

    fun deleteListItem(item: ListItemDTO) {
        viewModelScope.launch {
            productListRepository.deleteProductFromList(item)
        }
    }
}