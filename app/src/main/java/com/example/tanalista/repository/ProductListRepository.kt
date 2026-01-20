package com.example.tanalista.repository

import android.content.Context
import com.example.tanalista.database.ApplicationDatabase
import com.example.tanalista.database.model.dto.ListItemDTO
import com.example.tanalista.database.model.ProductEntity
import com.example.tanalista.database.model.ProductListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ProductListRepository(context: Context) {

    private val productListDao = ApplicationDatabase.getDatabase(context).ProductListDao()
    val productDao = ApplicationDatabase.getDatabase(context).ProductDao()

    suspend fun addOrUpdateProductInList(listItemDTO: ListItemDTO?) {

        if (listItemDTO == null) return

        val allProducts = productDao.getAllProducts().first()

        var existingProduct = allProducts.find {
            it.name.equals(listItemDTO.name.trim(), ignoreCase = true)
        }

        if (isUpdate(listItemDTO)) {
            existingProduct = allProducts.find {
                it.id == listItemDTO.productId && listItemDTO.listId.toInt() == listItemDTO.listId.toInt()
            }
        }

        existingProduct = getProductId(existingProduct, listItemDTO)

        val productListEntity =
            ProductListEntity(
                1 ,
                existingProduct.id,
                listItemDTO.name,
                listItemDTO.quantity,
                listItemDTO.productPrice,
                listItemDTO.isInCart,
                listItemDTO.category
            )

        productListDao.insert(productListEntity)
    }

    fun getAllListProducts(id: Long, isInCart: Boolean): Flow<List<ListItemDTO>> {
        return productListDao.getProductsInList(id, isInCart)
    }

    suspend fun addProductInCart(listItem: ListItemDTO) {

        val listItem = productListDao.getProductInListByIds(listItem.listId, listItem.productId)

        listItem.isInCart = true

        productListDao.insert(listItem)
    }

    suspend fun removeProductInCart(listItem: ListItemDTO) {

        val listItem = productListDao.getProductInListByIds(listItem.listId, listItem.productId)

        listItem.isInCart = false

        productListDao.insert(listItem)
    }

    private suspend fun getProductId(product: ProductEntity?, listItemDTO: ListItemDTO): ProductEntity {

        if (product != null) {
            return product
        }

        val newProduct = ProductEntity(listItemDTO.name, listItemDTO.category)

        productDao.insertProduct(newProduct)
        return newProduct
    }

    suspend fun deleteProductFromList(listItem: ListItemDTO) {
        val listItem = productListDao.getProductInListByIds(listItem.listId, listItem.productId)

        productListDao.deleteProductList(listItem)
    }

    private fun isUpdate(listItemDTO: ListItemDTO): Boolean =
        listItemDTO.productId.toInt() != 0 && listItemDTO.listId.toInt() != 0

    fun getAllProductsFromListOrderByAlphabetical(id: Long, isInCart: Boolean): Flow<List<ListItemDTO>> {
        return productListDao.getProductsInListOrderByAlphabetical(id, isInCart)
    }

    fun getAllProductsFromListOrderByCategory(id: Long, isInCart: Boolean): Flow<List<ListItemDTO>> {
        return productListDao.getProductsInListOrderByCategory(id, isInCart)
    }

}