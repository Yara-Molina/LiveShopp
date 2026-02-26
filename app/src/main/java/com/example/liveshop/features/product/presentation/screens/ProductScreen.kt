package com.example.liveshop.features.product.presentation.screens

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
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.presentation.components.AddProductDialog
import com.example.liveshop.features.product.presentation.components.ProductList
import com.example.liveshop.features.product.presentation.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    listId: String,
) {
    viewModel.setList(listId)
    val uiState by viewModel.uiState.collectAsState()

    ProductScreen(uiState = uiState, onAddButtonClick = { product ->
        viewModel.createProduct(product)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    uiState: ProductUIState,
    onAddButtonClick: (Product) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddProductDialog(
            listId = uiState.listId,
            onConfirm = {
                onAddButtonClick(it)
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
    ) {
        Box(modifier = Modifier.padding(it)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                ProductList(products = uiState.products)
            }
        }
    }
}
