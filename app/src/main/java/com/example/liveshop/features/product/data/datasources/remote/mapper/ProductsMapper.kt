package com.example.liveshop.features.product.data.datasources.remote.mapper

import com.example.liveshop.features.product.data.datasources.remote.models.ProductResponse
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus

fun ProductResponse.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        list_id = this.list_id,
        quantity = this.quantity,

        status = try {
            ProductStatus.valueOf(this.status.uppercase())
        } catch (e: Exception) {
            ProductStatus.PENDING
        },
        created_at = this.created_at
    )
}