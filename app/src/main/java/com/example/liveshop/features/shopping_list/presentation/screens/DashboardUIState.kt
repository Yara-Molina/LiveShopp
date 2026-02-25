package com.example.liveshop.features.shopping_list.presentation.screens

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList


    data class DashboardUiState(
        val isLoading: Boolean = false,
        val lists: List<ShoppingList> = emptyList(),
        val error: String? = null
    )
