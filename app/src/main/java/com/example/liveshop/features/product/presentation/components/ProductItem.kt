package com.example.liveshop.features.product.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.liveshop.features.product.domain.entities.Product

@Composable
fun ProductItem(product: Product) {
    Text(text = product.name)
}
