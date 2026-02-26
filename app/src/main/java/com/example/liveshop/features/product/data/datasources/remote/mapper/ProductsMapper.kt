package com.example.liveshop.features.product.data.datasources.remote.mapper

import com.example.liveshop.features.product.data.datasources.remote.models.ProductResponse
import com.example.liveshop.features.product.domain.entities.Product


fun ProductResponse.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        list_id = this.list_id,
        quantity = this.quantity,
        status = this.status,
        created_at = this.created_at
    )
}