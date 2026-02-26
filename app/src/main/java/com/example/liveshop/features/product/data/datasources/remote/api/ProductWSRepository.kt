package com.example.liveshop.features.product.data.datasources.remote.api

import com.example.liveshop.features.product.data.datasources.remote.models.ProductResponse
import kotlinx.coroutines.flow.Flow

interface ProductWSRepository {
    fun observeProducts(listId: String): Flow<List<ProductResponse>>
}