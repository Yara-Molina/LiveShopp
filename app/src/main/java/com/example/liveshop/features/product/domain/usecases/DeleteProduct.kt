package com.example.liveshop.features.product.domain.usecases

import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val repo: ProductsRepository
) {
    suspend operator fun invoke(productId: String) {
        repo.deleteProduct(productId)
    }
}