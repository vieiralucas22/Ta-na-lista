package com.example.tanalista.ui.viewmodel.dialog

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.tanalista.R
import com.example.tanalista.model.database.model.ProductEntity
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.enums.ProductCategory
import com.example.tanalista.repository.local.interfaces.IProductCategoryRepository
import com.example.tanalista.repository.local.interfaces.IProductListRepository
import com.example.tanalista.repository.local.interfaces.IProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDialogViewModel @Inject constructor(
    application: Application,
    val productCategoryRepository: IProductCategoryRepository,
    val productListRepository: IProductListRepository,
    val productRepository: IProductRepository
) : AndroidViewModel(application) {

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
    var category by mutableStateOf(application.getText(R.string.select_category).toString())
    var canAddToCart by mutableStateOf(false)
    var isInvalidProductName by mutableStateOf(false)
    var isInvalidQuantity by mutableStateOf(false)
    var isInvalidPrice by mutableStateOf(false)
    var textButtonDialog by mutableStateOf(application.getText(R.string.add).toString())
    var headerDialog by mutableStateOf(application.getText(R.string.add_item).toString())

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

        textButtonDialog = application.getText(R.string.update).toString()
        headerDialog = application.getText(R.string.update_item).toString()
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
        category = application.getText(R.string.select_category).toString()
        canAddToCart = false
        isInvalidProductName = false
        textButtonDialog = application.getText(R.string.add).toString()
        headerDialog = application.getText(R.string.add_item).toString()
        currentListItem = null
    }

    fun createNewItem(): ListItemDTO {
        return ListItemDTO(
            name = productName,
            quantity = quantity.ifEmpty { "0" }.toInt(),
            productPrice = price.ifEmpty { "0.0" }.toDouble(),
            category = if (category == application.getText(R.string.select_category)
                    .toString()
            ) ProductCategory.Undefined.toString() else category,
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
            category = if (category == application.getText(R.string.select_category)
                    .toString()
            ) ProductCategory.Undefined.toString() else category,
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