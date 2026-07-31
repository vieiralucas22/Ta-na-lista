package com.example.tanalista.model.repository.local.implementation

import com.example.tanalista.concurrency.CoroutineDispatcherProvider
import com.example.tanalista.model.database.dao.ProductDao
import com.example.tanalista.model.database.dao.ProductListDao
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.model.database.model.ProductEntity
import com.example.tanalista.model.database.model.ProductListEntity
import com.example.tanalista.model.repository.local.interfaces.ProductListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
    private val productListDAO: ProductListDao,
    private val productDAO: ProductDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ProductListRepository {

    /* Override Methods */

    override suspend fun addOrUpdateProductInList(listItemDTO: ListItemDTO?) =
        withContext(coroutineDispatcherProvider.io) {

            if (listItemDTO == null)
                return@withContext

            val allProducts = productDAO.getAllProducts().first()

            var existingProduct = allProducts.find {
                it.name.equals(listItemDTO.name.trim(), ignoreCase = true)
            }

            if (isUpdate(listItemDTO)) {
                existingProduct = allProducts.find {
                    it.id == listItemDTO.productId
                            && listItemDTO.listId.toInt() == listItemDTO.listId.toInt()
                }
            }

            existingProduct = getProductId(existingProduct, listItemDTO)

            val productListEntity =
                ProductListEntity(
                    1,
                    existingProduct.id,
                    listItemDTO.name,
                    listItemDTO.quantity,
                    listItemDTO.productPrice,
                    listItemDTO.isInCart,
                    listItemDTO.category
                )

            productListDAO.insert(productListEntity)
        }

    override suspend fun addProductToCart(item: ListItemDTO) =
        withContext(coroutineDispatcherProvider.io) {
            val listItem = productListDAO.getProductInListByIds(item.listId, item.productId)

            listItem.isInCart = true

            productListDAO.insert(listItem)
        }

    override fun getAllListProducts(id: Long, isInCart: Boolean): Flow<List<ListItemDTO>> =
        productListDAO.getProductsInList(id, isInCart)

    override suspend fun removeProductFromCart(item: ListItemDTO) =
        withContext(coroutineDispatcherProvider.io) {
            val listItem = productListDAO.getProductInListByIds(item.listId, item.productId)

            listItem.isInCart = false

            productListDAO.insert(listItem)
        }

    override suspend fun deleteProductFromList(item: ListItemDTO) =
        withContext(coroutineDispatcherProvider.io) {
            val listItem = productListDAO.getProductInListByIds(item.listId, item.productId)

            productListDAO.deleteProductList(listItem)
        }

    /* Private Methods */

    private fun getProductId(
        product: ProductEntity?,
        listItemDTO: ListItemDTO
    ): ProductEntity {

        if (product != null) {
            return product
        }

        val newProduct = ProductEntity(listItemDTO.name, listItemDTO.category)

        val id = productDAO.insertProduct(newProduct)
        newProduct.id = id

        return newProduct
    }

    private fun isUpdate(listItemDTO: ListItemDTO): Boolean =
        listItemDTO.productId.toInt() != 0 && listItemDTO.listId.toInt() != 0

}