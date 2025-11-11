package com.example.tanalista.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.tanalista.database.model.ProductEntity
import com.example.tanalista.repository.ProductListRepository

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private val productListRepository = ProductListRepository(application.applicationContext)

    val allProductsInCurrentList = productListRepository.getAllProductsFromList(1).asLiveData()

    fun moveProductBetweenLists(isChecked: Boolean, product: ProductEntity) {

//        viewModelScope.launch {
//            if (isChecked)
//                //productRepository.addProductToCart(product)
//            else
//                //productRepository.removeProductFromCart(product)
//        }
    }
}