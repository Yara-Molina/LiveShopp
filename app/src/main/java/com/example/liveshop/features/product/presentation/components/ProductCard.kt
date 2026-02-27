package com.example.liveshop.features.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus

private val Indigo900 = Color(0xFF1E1B4B)
private val Indigo500 = Color(0xFF6366F1)
private val Indigo100 = Color(0xFFE0E7FF)
private val RedSoft   = Color(0xFFFFE4E4)
private val RedAccent = Color(0xFFEF4444)

// Colores por estado
private val StatusPending   = Color(0xFFFFF7ED) to Color(0xFFF97316)  // fondo / acento
private val StatusBought    = Color(0xFFF0FDF4) to Color(0xFF22C55E)
private val StatusNotFound  = Color(0xFFFFF1F2) to Color(0xFFEF4444)

@Composable
fun ProductCard(
    product: Product,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onStatusChange: (ProductStatus) -> Unit
) {
    val (cardBg, accentColor) = when (product.status) {
        ProductStatus.PENDING   -> StatusPending
        ProductStatus.BOUGHT    -> StatusBought
        ProductStatus.NOT_FOUND -> StatusNotFound
    }

    val statusLabel = when (product.status) {
        ProductStatus.PENDING   -> "Pendiente"
        ProductStatus.BOUGHT    -> "Comprado"
        ProductStatus.NOT_FOUND -> "No encontrado"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                // Indicador de color de estado
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (product.status) {
                            ProductStatus.PENDING   -> Icons.Outlined.Schedule
                            ProductStatus.BOUGHT    -> Icons.Filled.CheckCircle
                            ProductStatus.NOT_FOUND -> Icons.Filled.Close
                        },
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Indigo900
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Cant: ${product.quantity}  •  ",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Text(
                            text = statusLabel,
                            style = MaterialTheme.typography.bodySmall,
                            color = accentColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Botón editar
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Indigo100)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Indigo500,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(Modifier.width(6.dp))

                // Botón eliminar
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(RedSoft)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = RedAccent,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Chips de estado
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    ProductStatus.PENDING   to "Pendiente",
                    ProductStatus.BOUGHT    to "Comprado",
                    ProductStatus.NOT_FOUND to "No hallado"
                ).forEach { (status, label) ->
                    val selected = product.status == status
                    val (chipBg, chipAccent) = when (status) {
                        ProductStatus.PENDING   -> StatusPending
                        ProductStatus.BOUGHT    -> StatusBought
                        ProductStatus.NOT_FOUND -> StatusNotFound
                    }
                    FilterChip(
                        selected = selected,
                        onClick = { onStatusChange(status) },
                        label = {
                            Text(
                                text = label,
                                fontSize = 12.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = chipBg,
                            selectedLabelColor = chipAccent,
                            containerColor = Color(0xFFF3F4F6),
                            labelColor = Color.Gray
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selected,
                            selectedBorderColor = chipAccent.copy(alpha = 0.4f),
                            borderColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}