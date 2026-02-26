package com.example.liveshop.features.product.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import com.example.liveshop.features.product.presentation.screens.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private var productsJob: Job? = null
    private var currentListId: String? = null

    fun setList(listId: String) {
        if (currentListId == listId) return
        currentListId = listId

        productsJob?.cancel()
        _uiState.update { it.copy(isLoading = true) }

        productsJob = viewModelScope.launch {
            repository.observeProducts(listId).collect { productList ->
                // SOLUCIÓN: Comparamos Enum con Enum directamente
                val visibleProducts = productList
                _uiState.update {
                    it.copy(products = visibleProducts, isLoading = false)
                }
            }
        }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.createProduct(product)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al crear producto") }
            }
        }
    }

    fun updateProduct(productId: String, product: Product) {
        viewModelScope.launch {
            try {
                repository.updateProduct(productId, product)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al actualizar") }
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                // Pasamos el Enum directamente al repositorio
                repository.deleteProduct(productId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "No se pudo eliminar") }
            }
        }
    }

    fun updateProductStatus(productId: String, status: ProductStatus) {
        viewModelScope.launch {
            try {
                repository.updateStatus(productId, status)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al actualizar estado") }
            }
        }
    }
}