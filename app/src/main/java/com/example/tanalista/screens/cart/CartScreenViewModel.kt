package com.example.tanalista.screens.cart

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.R
import com.example.tanalista.enums.ProductCategory
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.repository.local.interfaces.IProductListRepository
import com.example.tanalista.screens.BaseViewModel
import com.example.tanalista.screens.cart.model.ButtonState
import com.example.tanalista.screens.cart.model.CartScreenState
import com.example.tanalista.screens.cart.model.HeaderToggleButtonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    application: Application,
    val productListRepository: IProductListRepository
) : BaseViewModel(application) {

    var state by mutableStateOf(CartScreenState())

    init {
        initializeState()
    }

    private fun initializeState() {
        viewModelScope.launch {

            val allProducts = productListRepository.getAllListProducts(1)

            state = CartScreenState(
                totalCartValue = 0.0,
                cartToggle = HeaderToggleButtonState(
                    R.drawable.ic_cart,
                    false,
                    ButtonState(R.string.cart)
                ),
                listToggle = HeaderToggleButtonState(
                    R.drawable.ic_list,
                    true,
                    ButtonState(R.string.list)
                ),
                allProductsInList = allProducts
            )

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

            state = state.copy(
                cartToggle = state.cartToggle.copy(isChecked = false),
                listToggle = state.listToggle.copy(isChecked = true),
                allProductsInList = allProducts
            )
        }
    }

    fun showAllProductsInCart() {
        viewModelScope.launch {
            val allProducts = productListRepository.getAllListProducts(1, true)

            state = state.copy(
                cartToggle = state.cartToggle.copy(isChecked = true),
                listToggle = state.listToggle.copy(isChecked = false),
                allProductsInList = allProducts
            )
        }
    }

    suspend fun updateTotalCartValue() {
        productListRepository.getAllListProducts(1, true)
            .collect { products ->
                var total = 0.0
                products.forEach { product ->
                    total += product.quantity * product.productPrice
                }
                state = state.copy(totalCartValue = total)
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

    fun deleteListItem(item: ListItemDTO) {
        viewModelScope.launch {
            productListRepository.deleteProductFromList(item)
        }
    }

}