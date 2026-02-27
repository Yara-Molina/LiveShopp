package com.example.liveshop.features.product.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus

private val Indigo900 = Color(0xFF1E1B4B)
private val Indigo500 = Color(0xFF6366F1)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    listId: String,
    initialProduct: Product? = null,
    onConfirm: (Product) -> Unit,
    onDismiss: () -> Unit
) {
    var name     by remember { mutableStateOf(initialProduct?.name ?: "") }
    var quantity by remember { mutableStateOf(initialProduct?.quantity?.toString() ?: "1") }

    val isEditing = initialProduct != null
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Indigo500,
        focusedLabelColor = Indigo500,
        cursorColor = Indigo500
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = if (isEditing) "Editar producto" else "Nuevo producto",
                fontWeight = FontWeight.Bold,
                color = Indigo900
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del producto") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Cantidad") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = fieldColors
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(
                            Product(
                                id = initialProduct?.id ?: "",
                                list_id = listId,
                                name = name,
                                quantity = quantity.toIntOrNull() ?: 1,
                                status = initialProduct?.status ?: ProductStatus.PENDING,
                                created_at = initialProduct?.created_at ?: ""
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Indigo500),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(if (isEditing) "Guardar" else "Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = Indigo500)
            }
        }
    )
}