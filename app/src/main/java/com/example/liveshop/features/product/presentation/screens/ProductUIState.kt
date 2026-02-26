package com.example.liveshop.features.product.presentation.screens

import com.example.liveshop.features.product.domain.entities.Product

data class ProductUIState(
    val listId: String = "",
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList()
)
