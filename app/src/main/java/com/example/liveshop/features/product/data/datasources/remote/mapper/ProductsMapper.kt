package com.example.liveshop.features.product.data.datasources.remote.mapper

import com.example.liveshop.features.product.data.datasources.remote.models.ProductResponse
import com.example.liveshop.features.product.data.local.entities.ProductEntity
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus


fun ProductResponse.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        list_id = this.list_id,
        quantity = this.quantity,
        status = mapStatus(this.status),
        created_at = this.created_at
    )
}


fun ProductResponse.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        listId = this.list_id,
        name = this.name,
        quantity = this.quantity,
        status = this.status.uppercase()
    )
}


fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        list_id = this.listId,
        quantity = this.quantity,
        status = mapStatus(this.status),
        created_at = ""
    )
}


private fun mapStatus(status: String): ProductStatus {
    return try {
        ProductStatus.valueOf(status.uppercase())
    } catch (e: Exception) {
        ProductStatus.PENDING
    }
}