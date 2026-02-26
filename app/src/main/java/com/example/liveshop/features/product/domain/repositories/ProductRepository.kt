package com.example.liveshop.features.product.domain.repositories

import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun observeProducts(listId: String): Flow<List<Product>>
    suspend fun createProduct(product: Product): Product
    suspend fun updateStatus(productId: String, status: ProductStatus)
    suspend fun deleteProduct(productId: String)
    suspend fun updateProduct(productId: String, product: Product): Product
}