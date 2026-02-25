package com.example.liveshop.features.product.domain.usecases

import com.example.liveshop.features.product.domain.entities.ProductStatus
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import javax.inject.Inject

class UpdateStatus @Inject constructor(
    private val repo: ProductsRepository
) {
    suspend operator fun invoke(productId: String, status: ProductStatus) {
        repo.updateStatus(productId, status)
    }
}