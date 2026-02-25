package com.example.liveshop.features.product.data.repositories

import com.example.liveshop.features.product.data.datasources.remote.api.ProductHTTPApi
import com.example.liveshop.features.product.data.datasources.remote.api.ProductWSApi
import com.example.liveshop.features.product.data.datasources.remote.mapper.toDomain
import com.example.liveshop.features.product.data.datasources.remote.models.ProductRequest
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val api: ProductHTTPApi,
    private val webSocket: ProductWSApi,
) : ProductsRepository {

    override fun observeProducts(listId: String): Flow<List<Product>> {
        return webSocket.observeProducts(listId)
            .map { dtoList ->
                dtoList.map { it.toDomain() }
            }
    }

    override suspend fun createProduct(product: Product): Product {
        val product = ProductRequest(
            product.list_id,
            product.name,
            product.quantity
        )
        val response = api.createProduct(product)
        return response.toDomain()
    }

    override suspend fun updateStatus(
        productId: String,
        status: ProductStatus
    ) {
        api.updateStatus(
            productId,
            status.toString()
        )
    }

    override suspend fun deleteProduct(productId: String) {
        api.deleteProduct(productId)
    }
}