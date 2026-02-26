package com.example.liveshop.features.product.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import com.example.liveshop.features.product.presentation.components.AddProductDialog
import com.example.liveshop.features.product.presentation.components.ProductList
import com.example.liveshop.features.product.presentation.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    listId: String,
) {
    Log.d("PRODUCT_FLOW", "ProductScreen received listId: $listId")
    viewModel.setList(listId)
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Log.d("PRODUCT_FLOW", "ProductScreen recomposing. isLoading: ${uiState.isLoading}, products: ${uiState.products.size}")

    if (showDialog) {
        AddProductDialog(
            listId = listId, // Use listId from arguments, not from uiState
            onConfirm = {
                Log.d("PRODUCT_FLOW", "Creating product: $it")
                viewModel.createProduct(it)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Products") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new product")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                ProductList(products = uiState.products)
            }
        }
    }
}
