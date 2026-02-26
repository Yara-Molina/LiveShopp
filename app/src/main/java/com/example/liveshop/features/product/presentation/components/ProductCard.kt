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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.ui.text.style.TextDecoration // Para tachar el texto
import com.example.liveshop.features.product.domain.entities.ProductStatus // Importa tus estatus

@Composable
fun ProductCard(
    product: Product,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onStatusChange: (ProductStatus) -> Unit
) {

    val containerColor = when (product.status) {
        ProductStatus.NOT_FOUND -> Color(0xFFFFCDD2)   // rojo claro
        ProductStatus.PENDING -> Color(0xFFFFE0B2)     // naranja claro
        ProductStatus.BOUGHT -> Color(0xFFC8E6C9)      // verde claro
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Cantidad: ${product.quantity}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                }

                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                FilterChip(
                    selected = product.status == ProductStatus.PENDING,
                    onClick = { onStatusChange(ProductStatus.PENDING) },
                    label = { Text("Pendiente") }
                )

                FilterChip(
                    selected = product.status == ProductStatus.BOUGHT,
                    onClick = { onStatusChange(ProductStatus.BOUGHT) },
                    label = { Text("Comprado") }
                )

                FilterChip(
                    selected = product.status == ProductStatus.NOT_FOUND,
                    onClick = { onStatusChange(ProductStatus.NOT_FOUND) },
                    label = { Text("No encontrado") }
                )
            }
        }
    }
}