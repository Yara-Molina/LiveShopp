package com.example.liveshop.features.product.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.liveshop.features.product.domain.entities.Product

import androidx.compose.foundation.clickable
import androidx.compose.ui.text.style.TextDecoration // Para tachar el texto
import com.example.liveshop.features.product.domain.entities.ProductStatus // Importa tus estatus

@Composable
fun ProductCard(
    product: Product,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onToggleStatus: () -> Unit
) {

    val isBought = product.status == ProductStatus.BOUGHT

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onToggleStatus() },
        colors = CardDefaults.cardColors(

            containerColor = if (isBought) Color(0xFFF5F5F5) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isBought) 0.dp else 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,

                    textDecoration = if (isBought) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (isBought) Color.Gray else Color.Unspecified
                )
                Text(
                    text = "Cantidad: ${product.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Solo mostrar botones de edición si NO está comprado (opcional)
            if (!isBought) {
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFF7C3AED))
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFFFF4D4D))
                    }
                }
            } else {
                // Si está comprado, mostramos el icono de eliminar por si acaso
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.LightGray)
                }
            }
        }
    }
}