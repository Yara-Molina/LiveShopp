package com.example.liveshop.features.product.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.presentation.components.AddProductDialog
import com.example.liveshop.features.product.presentation.components.ProductCard
import com.example.liveshop.features.product.presentation.components.ProductList
import com.example.liveshop.features.product.presentation.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    listId: String,
) {
    viewModel.setList(listId)
    val uiState by viewModel.uiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

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
        topBar = {
            TopAppBar(title = { Text(text = "Productos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingProduct = null // Resetear para que sea creación
                showDialog = true
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new product")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                // Reemplazamos ProductList por un LazyColumn directo para usar ProductCard
                androidx.compose.foundation.lazy.LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.products) { product ->
                        ProductCard(
                            product = product,
                            onDelete = { viewModel.deleteProduct(product.id) },
                            onEdit = {
                                editingProduct = product
                                showDialog = true
                            },
                            onToggleStatus = { viewModel.toggleProductBought(product) }
                        )
                    }
                }
            }
        }
    }
}

