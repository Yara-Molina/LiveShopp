package com.example.liveshop.features.product.presentation.screens

import com.example.liveshop.features.product.domain.entities.Product

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)