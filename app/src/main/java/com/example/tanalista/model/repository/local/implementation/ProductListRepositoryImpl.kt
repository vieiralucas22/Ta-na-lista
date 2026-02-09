package com.example.tanalista.repository.local.implementation

import com.example.tanalista.model.database.dao.ProductDao
import com.example.tanalista.model.database.dao.ProductListDao
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.model.database.model.ProductEntity
import com.example.tanalista.model.database.model.ProductListEntity
import com.example.tanalista.repository.local.interfaces.IProductListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
    val productListDAO: ProductListDao,
    val productDAO: ProductDao
) : IProductListRepository {

    /* Override Methods */

    override suspend fun addOrUpdateProductInList(listItemDTO: ListItemDTO?) {

        if (listItemDTO == null) return

        val allProducts = productDAO.getAllProducts().first()

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

    override suspend fun addProductInCart(listItem: ListItemDTO) {

        val listItem = productListDAO.getProductInListByIds(listItem.listId, listItem.productId)

        listItem.isInCart = true

        productListDAO.insert(listItem)
    }

    override fun getAllListProducts(
        id: Long,
        isInCart: Boolean
    ): Flow<List<ListItemDTO>> {
        return productListDAO.getProductsInList(id, isInCart)
    }

    override suspend fun deleteProductInCart(listItem: ListItemDTO) {

        val listItem = productListDAO.getProductInListByIds(listItem.listId, listItem.productId)

        listItem.isInCart = false

        productListDAO.insert(listItem)
    }

    override suspend fun deleteProductFromList(listItem: ListItemDTO) {
        val listItem = productListDAO.getProductInListByIds(listItem.listId, listItem.productId)

        productListDAO.deleteProductList(listItem)
    }

    /* Private Methods */

    private suspend fun getProductId(
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