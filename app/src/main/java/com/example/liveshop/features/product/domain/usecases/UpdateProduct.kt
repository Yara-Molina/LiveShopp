package com.example.liveshop.features.product.domain.usecases

import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import javax.inject.Inject

class UpdateProduct @Inject constructor(
    private val repo: ProductsRepository
) {
    suspend operator fun invoke(productId: String, product: Product) {
        repo.updateProduct(productId, product)
    }
}