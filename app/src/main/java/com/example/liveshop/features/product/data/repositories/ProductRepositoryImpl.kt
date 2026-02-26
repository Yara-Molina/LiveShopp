package com.example.liveshop.features.product.data.repositories

import com.example.liveshop.features.product.data.datasources.remote.api.ProductHTTPApi
import com.example.liveshop.features.product.data.datasources.remote.api.ProductWSRepository
import com.example.liveshop.features.product.data.datasources.remote.mapper.toDomain
import com.example.liveshop.features.product.data.datasources.remote.models.ProductRequest
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductHTTPApi,
    private val webSocket: ProductWSRepository,
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

    override suspend fun updateProduct(productId: String, product: Product): Product {
        val product = ProductRequest(
            product.list_id,
            product.name,
            product.quantity
        )
        val response = api.updateProduct(productId, product)
        return response.toDomain()
    }

    override suspend fun deleteProduct(productId: String) {
        api.deleteProduct(productId)
    }
}