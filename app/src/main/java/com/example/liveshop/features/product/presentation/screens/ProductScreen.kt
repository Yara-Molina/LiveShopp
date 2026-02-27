package com.example.liveshop.features.product.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.liveshop.core.navigation.Dashboard
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.presentation.components.AddProductDialog
import com.example.liveshop.features.product.presentation.components.ProductCard
import com.example.liveshop.features.product.presentation.viewmodels.ProductViewModel

private val Indigo900  = Color(0xFF1E1B4B)
private val Indigo700  = Color(0xFF3730A3)
private val Indigo500  = Color(0xFF6366F1)
private val Indigo300  = Color(0xFFA5B4FC)
private val Surface    = Color(0xFFF8F7FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    listId: String,
    navController: NavController
) {
    viewModel.setList(listId)
    val uiState by viewModel.uiState.collectAsState()

    var showDialog     by remember { mutableStateOf(false) }
    var editingProduct by remember { mutableStateOf<Product?>(null) }

    if (showDialog) {
        AddProductDialog(
            listId = listId,
            initialProduct = editingProduct,
            onConfirm = { product ->
                if (editingProduct == null) {
                    viewModel.createProduct(product)
                } else {
                    viewModel.updateProduct(editingProduct!!.id, product)
                }
                showDialog = false
                editingProduct = null
            },
            onDismiss = {
                showDialog = false
                editingProduct = null
            }
        )
    }

    Scaffold(
        containerColor = Surface,
        topBar = {
            // Header con mismo gradiente que DashboardScreen
            Box(
                modifier = Modifier
                    .background(Brush.linearGradient(listOf(Indigo900, Indigo700)))
                    .padding(bottom = 4.dp)
            ) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "PRODUCTOS",
                                color = Indigo300,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 3.sp
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = "Mi Lista",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Dashboard) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Regresar",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingProduct = null
                    showDialog = true
                },
                containerColor = Indigo500,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(6.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Indigo500
                )
            } else if (uiState.products.isEmpty()) {
                // Empty state coherente con Dashboard
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("📦", fontSize = 48.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Sin productos aún",
                        color = Indigo300,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    Text(
                        "Toca + para agregar uno",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    items(uiState.products) { product ->
                        ProductCard(
                            product = product,
                            onDelete = { viewModel.deleteProduct(product.id) },
                            onEdit = {
                                editingProduct = product
                                showDialog = true
                            },
                            onStatusChange = { newStatus ->
                                viewModel.updateProductStatus(product.id, newStatus)
                            }
                        )
                    }
                }
            }
        }
    }
}