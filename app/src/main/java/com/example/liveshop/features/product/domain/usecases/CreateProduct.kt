package com.example.liveshop.features.product.domain.usecases

import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import javax.inject.Inject

class CreateProduct @Inject constructor(
    private val repo: ProductsRepository
){
    suspend operator fun invoke(product: Product): Product {
        return repo.createProduct(product)
    }
}