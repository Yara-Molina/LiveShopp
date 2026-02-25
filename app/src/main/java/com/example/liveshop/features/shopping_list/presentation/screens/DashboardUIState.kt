package com.example.liveshop.features.shopping_list.presentation.screens

import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList

sealed class DashboardUIState {
    object Loading : DashboardUIState()
    data class Success(val lists: List<ShoppingList>) : DashboardUIState()
    data class Error(val message: String) : DashboardUIState()
}