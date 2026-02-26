package com.example.liveshop.features.product.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    listId: String,
    initialProduct: Product? = null,
    onConfirm: (Product) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(initialProduct?.name ?: "") }
    var quantity by remember { mutableStateOf(initialProduct?.quantity?.toString() ?: "1") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (initialProduct == null) "Add new product" else "Edit product") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Product name") }
                )
                TextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Solo procedemos si el nombre no está vacío
                    if (name.isNotBlank()) {
                        val product = Product(
                            id = initialProduct?.id ?: "",
                            list_id = listId,
                            name = name,
                            quantity = quantity.toIntOrNull() ?: 1,
                            // CORRECCIÓN: Usamos el Enum directamente
                            status = initialProduct?.status ?: ProductStatus.PENDING,
                            created_at = initialProduct?.created_at ?: ""
                        )
                        onConfirm(product)
                    }
                }
            ) {
                Text(if (initialProduct == null) "Add" else "Update")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
