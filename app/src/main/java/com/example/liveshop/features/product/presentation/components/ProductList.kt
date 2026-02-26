package com.example.liveshop.features.product.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.liveshop.features.product.domain.entities.Product

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn {
        items(products) {
            ProductItem(product = it)
        }
    }
}