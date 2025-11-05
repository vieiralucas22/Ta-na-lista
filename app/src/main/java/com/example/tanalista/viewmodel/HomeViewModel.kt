package com.example.tanalista.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tanalista.database.model.ProductEntity
import com.example.tanalista.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private val productRepository = ProductRepository(application.applicationContext)

    val allProducts = productRepository.getAllProducts().asLiveData()

    fun moveProductBetweenLists(isChecked: Boolean, product: ProductEntity) {

        viewModelScope.launch {
            if (isChecked)
                productRepository.addProductToCart(product)
            else
                productRepository.removeProductFromCart(product)
        }
    }

}