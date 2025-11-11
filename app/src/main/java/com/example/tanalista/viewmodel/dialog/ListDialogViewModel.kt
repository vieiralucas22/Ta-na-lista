package com.example.tanalista.viewmodel.dialog

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanalista.database.model.dto.ListItemDTO
import com.example.tanalista.repository.ProductCategoryRepository
import com.example.tanalista.repository.ProductListRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListDialogViewModel(application: Application) : AndroidViewModel(application) {

    private val productCategoryRepository =
        ProductCategoryRepository(application.applicationContext)
    private val productListRepository = ProductListRepository(application.applicationContext)

    val allCategories = productCategoryRepository.getAllCategories().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    var isDialogOpen by mutableStateOf(false)
    var productName by  mutableStateOf("")
    var quantity by  mutableStateOf("")
    var price by mutableStateOf("")
    var isDropdownExpanded by mutableStateOf(false)
    var category by  mutableStateOf("Select a category")
    var canAddToCart by mutableStateOf(false)
    var isInvalid by mutableStateOf(false)

    fun openDialog() {
        isDialogOpen = true
    }

    fun closeDialog() {
        isDialogOpen = false
        clearFields()
    }

    fun addItemToList() {

        if (shouldAbortAddItemToList()) return

        price = price.replace(",",".")

        val listItemDTO = ListItemDTO (productName, quantity.toInt(), price.toDouble(), category, canAddToCart)

        viewModelScope.launch {
            productListRepository.addProductInList(listItemDTO)
        }

        clearFields()
    }

    fun handleWithQuantityValueChange(input : String)
    {
        quantity = input.replace(",", "").replace("-","").replace(" ", "").replace(".", "").trim()
    }

    fun handleWithPriceValueChange(input : String)
    {
        var filterInput = input.replace(",", ".").replace("-","").replace(" ", "").trim()

        if (filterInput == ".") {
            filterInput = "0."
        }

        val integerPart = filterInput.split(".")[0].trim()
        val decimalPart = filterInput.split(".").getOrNull(1)?.take(2) ?: ""

        price = when {
            filterInput.endsWith(".") && decimalPart.isEmpty() -> "$integerPart."
            decimalPart.isNotEmpty() -> "$integerPart.$decimalPart"
            else -> integerPart
        }
    }

    fun shouldAbortAddItemToList() : Boolean
    {
        isInvalid = productName.isEmpty()

        return productName.isEmpty()
    }

    fun clearFields()
    {
        productName = ""
        quantity = ""
        price = ""
        category = "Select a category"
        canAddToCart = false
        isInvalid = false
    }
}