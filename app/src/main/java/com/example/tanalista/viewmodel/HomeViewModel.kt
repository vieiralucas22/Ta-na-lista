package com.example.tanalista.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tanalista.database.model.ProductEntity

class HomeViewModel (application: Application) : AndroidViewModel(application) {



    fun moveProductBetweenLists(isChecked: Boolean, product: ProductEntity) {

//        viewModelScope.launch {
//            if (isChecked)
//                //productRepository.addProductToCart(product)
//            else
//                //productRepository.removeProductFromCart(product)
//        }
    }
}