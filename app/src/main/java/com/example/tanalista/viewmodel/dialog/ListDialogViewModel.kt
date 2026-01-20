package com.example.tanalista.viewmodel.dialog

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.database.model.ProductEntity
import com.example.tanalista.database.model.dto.ListItemDTO
import com.example.tanalista.enums.ProductCategory
import com.example.tanalista.repository.ProductCategoryRepository
import com.example.tanalista.repository.ProductListRepository
import com.example.tanalista.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListDialogViewModel(application: Application) : AndroidViewModel(application) {

    private val productCategoryRepository =
        ProductCategoryRepository(application.applicationContext)
    private val productListRepository = ProductListRepository(application.applicationContext)
    private val productRepository = ProductRepository(application.applicationContext)

    private var currentListItem: ListItemDTO? = null

    val allCategories = productCategoryRepository.getAllCategories().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
    private var allProducts: List<ProductEntity> = emptyList()

    var isDialogOpen by mutableStateOf(false)
    var productName by mutableStateOf("")
    var quantity by mutableStateOf("")
    var price by mutableStateOf("")
    var isCategoryDropdownExpanded by mutableStateOf(false)
    var isProductNameDropdownExpanded by mutableStateOf(false)
    var category by mutableStateOf("Escolha uma categoria")
    var canAddToCart by mutableStateOf(false)
    var isInvalidProductName by mutableStateOf(false)
    var isInvalidQuantity by mutableStateOf(false)
    var isInvalidPrice by mutableStateOf(false)
    var textButtonDialog by mutableStateOf("Adicionar")
    var headerDialog by mutableStateOf("Adicionar item")

    private val _productsSuggestion = MutableLiveData<List<ProductEntity>>()
    val productsSuggestion: LiveData<List<ProductEntity>> = _productsSuggestion

    init {
        viewModelScope.launch {
            productRepository.getAllProducts().collect { newList ->
                allProducts = newList
            }
        }
    }

    fun openDialog() {
        isDialogOpen = true
    }

    fun closeDialog() {
        isDialogOpen = false
        clearFields()
    }

    fun editDialog(listItem: ListItemDTO) {
        currentListItem = listItem

        productName = currentListItem?.name.toString()
        category = currentListItem?.category.toString()
        price = currentListItem?.productPrice.toString()
        quantity = currentListItem?.quantity.toString()
        canAddToCart = currentListItem?.isInCart == true

        textButtonDialog = "Atualizar"
        headerDialog = "Atualizar item!"
        openDialog()
    }

    fun addOrUpdateItemToList() {

        if (shouldAbortAddItemToList()) return

        price = price.replace(",", ".")

        currentListItem = if (currentListItem == null) createNewItem() else getItemUpdated()

        viewModelScope.launch {
            productListRepository.addOrUpdateProductInList(currentListItem)
        }

        closeDialog()
    }

    fun handleWithQuantityValueChange(input: String) {
        quantity = input.replace(",", "").replace("-", "").replace(" ", "").replace(".", "").trim()
    }

    fun handleWithPriceValueChange(input: String) {
        var filterInput = input.replace(",", ".").replace("-", "").replace(" ", "").trim()

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

    fun shouldAbortAddItemToList(): Boolean {
        isInvalidProductName = productName.isEmpty()

        return isInvalidProductName
    }

    fun clearFields() {
        productName = ""
        quantity = ""
        price = ""
        category = "Escolha uma categoria"
        canAddToCart = false
        isInvalidProductName = false
        textButtonDialog = "Adicionar"
        headerDialog = "Adicionar item"
        currentListItem = null
    }

    fun createNewItem(): ListItemDTO {
        return ListItemDTO(
            name = productName,
            quantity = quantity.ifEmpty { "0" }.toInt(),
            productPrice = price.ifEmpty { "0.0" }.toDouble(),
            category = if (category == "Escolha uma categoria") ProductCategory.Undefined.toString() else category,
            isInCart = canAddToCart
        )
    }

    fun getItemUpdated(): ListItemDTO {
        return ListItemDTO(
            productId = currentListItem!!.productId,
            listId = currentListItem!!.listId,
            name = productName,
            quantity = quantity.ifEmpty { "0" }.toInt(),
            productPrice = price.ifEmpty { "0.0" }.toDouble(),
            category = if (category == "Escolha uma categoria") ProductCategory.Undefined.toString() else category,
            isInCart = canAddToCart
        )
    }

    fun suggestNewProducts(inputTextName: String) {
        updateProductDropdownList(inputTextName)

        productName = inputTextName
    }

    fun updateProductDropdownList(inputTextName: String) {
        val newSuggestionProducts = allProducts
            .filter { it.name.lowercase().startsWith(inputTextName.lowercase()) }
            .take(5)
            .sortedBy { it.name }

        isProductNameDropdownExpanded = newSuggestionProducts.isNotEmpty()

        _productsSuggestion.value = newSuggestionProducts
    }
}