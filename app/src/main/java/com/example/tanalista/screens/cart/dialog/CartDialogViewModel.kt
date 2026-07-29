package com.example.tanalista.screens.cart.dialog

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.R
import com.example.tanalista.enums.ProductCategory
import com.example.tanalista.logger.Logger
import com.example.tanalista.model.database.model.ProductEntity
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.model.repository.local.interfaces.ProductCategoryRepository
import com.example.tanalista.model.repository.local.interfaces.ProductListRepository
import com.example.tanalista.repository.local.interfaces.ProductRepository
import com.example.tanalista.screens.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.ifEmpty
import kotlin.text.toDouble
import kotlin.text.toInt

@HiltViewModel
class CartDialogViewModel @Inject constructor(
    application: Application,
    val productCategoryRepository: ProductCategoryRepository,
    val productListRepository: ProductListRepository,
    val productRepository: ProductRepository
) : BaseViewModel(application) {

    private val TAG : String = "CartDialogViewModel"

    private var currentListItem: ListItemDTO? = null

    val allCategories = productCategoryRepository.getAllCategories().stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        emptyList()
    )
    private var allProducts: List<ProductEntity> = emptyList()

    var isDialogOpen by mutableStateOf(false)
    var productName by mutableStateOf("")
    var quantity by mutableStateOf("")
    var price by mutableStateOf("")
    var isCategoryDropdownExpanded by mutableStateOf(false)
    var isProductNameDropdownExpanded by mutableStateOf(false)
    var category by mutableStateOf(getText(R.string.select_category))
    var canAddToCart by mutableStateOf(false)
    var isInvalidProductName by mutableStateOf(false)
    var isInvalidQuantity by mutableStateOf(false)
    var isInvalidPrice by mutableStateOf(false)
    var textButtonDialog by mutableStateOf(getText(R.string.add))

    private val _productsSuggestion = MutableLiveData(
        listOf(
            ProductEntity(name = "Arroz", category = "Food"),
            ProductEntity(name = "Feijão", category = "Food"),
            ProductEntity(name = "Coca-Cola", category = "Drink"),
            ProductEntity(name = "Sabão", category = "Clean"),
            ProductEntity(name = "Papel Higiênico", category = "Toilet")
        )
    )
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

        textButtonDialog = getText(R.string.update)
        openDialog()
    }

    fun addOrUpdateItemToList() {

        if (shouldAbortAddItemToList()) return

        price = price.replace(",", ".")

        currentListItem = if (currentListItem == null) createNewItem() else getItemUpdated()

        viewModelScope.launch {
            try {
                productListRepository.addOrUpdateProductInList(currentListItem)
            } catch (e: Exception) {
                Logger.e(e, TAG, "Error to add or update item!")
            }
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
        category = getText(R.string.select_category)
        canAddToCart = false
        isInvalidProductName = false
        textButtonDialog = getText(R.string.add)
        currentListItem = null
    }

    fun createNewItem(): ListItemDTO {
        return ListItemDTO(
            name = productName,
            quantity = quantity.ifEmpty { "0" }.toInt(),
            productPrice = price.ifEmpty { "0.0" }.toDouble(),
            category = if (category == getText(R.string.select_category))
                ProductCategory.UNDEFINED.toString() else category,
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
            category = if (category == getText(R.string.select_category))
                ProductCategory.UNDEFINED.toString() else category,
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
            .take(3)
            .sortedBy { it.name }

        isProductNameDropdownExpanded = newSuggestionProducts.isNotEmpty()

        _productsSuggestion.value = newSuggestionProducts
    }
}