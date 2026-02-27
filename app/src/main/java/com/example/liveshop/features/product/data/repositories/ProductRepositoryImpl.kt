package com.example.liveshop.features.product.data.repositories

import android.util.Log
import com.example.liveshop.features.product.data.datasources.remote.api.ProductHTTPApi
import com.example.liveshop.features.product.data.datasources.remote.api.ProductWSRepository
import com.example.liveshop.features.product.data.datasources.remote.mapper.toDomain
import com.example.liveshop.features.product.data.datasources.remote.mapper.toEntity
import com.example.liveshop.features.product.data.datasources.remote.models.ProductRequest
import com.example.liveshop.features.product.data.local.dao.ProductDao

import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductHTTPApi,
    private val webSocket: ProductWSRepository,
    private val productDao: ProductDao
) : ProductsRepository {


    override fun observeProducts(listId: String): Flow<List<Product>> {
        return productDao.getProductsFlow(listId)
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }


    override suspend fun startRealtimeSync(listId: String) {
        webSocket.observeProducts(listId).collect { dtoList ->
            val entities = dtoList.map { it.toEntity() }
            productDao.insertProducts(entities)
        }
    }



    override suspend fun createProduct(product: Product): Product {
        val request = ProductRequest(product.list_id, product.name, product.quantity)
        val response = api.createProduct(request)

        return response.toDomain()
    }

    override suspend fun updateStatus(productId: String, status: ProductStatus) {

        productDao.updateProductStatus(productId, status.name)

        try {

            api.updateStatus(productId, status.name)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateProduct(productId: String, product: Product): Product {
        val request = ProductRequest(product.list_id, product.name, product.quantity)
        val response = api.updateProduct(productId, request)
        return response.toDomain()
    }

    override suspend fun deleteProduct(productId: String) {

        productDao.deleteProductById(productId)

        try {

            api.deleteProduct(productId)
        } catch (e: Exception) {
            Log.e("API_ERROR", "El servidor falló pero ya lo quitamos de la vista")
        }
    }
}