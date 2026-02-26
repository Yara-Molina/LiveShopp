package com.example.liveshop.features.product.domain.usecases

import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import javax.inject.Inject

class ObserveProducts @Inject constructor(
    private val repo: ProductsRepository
) {
    operator fun invoke(listId: String) = repo.observeProducts(listId)
}