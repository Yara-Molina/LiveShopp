package com.example.liveshop.features.product.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import com.example.liveshop.features.product.presentation.screens.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    // PILAR 2.C: SharedFlow para eventos únicos (errores/alertas)
    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    private var productsJob: Job? = null
    private var syncJob: Job? = null
    private var currentListId: String? = null

    fun setList(listId: String) {
        if (currentListId == listId) return
        currentListId = listId

        // Cancelamos procesos previos para evitar fugas de memoria o datos mezclados
        productsJob?.cancel()
        syncJob?.cancel()

        _uiState.update { it.copy(isLoading = true) }

        // 1. INICIAR SINCRONIZACIÓN (WebSocket -> Room)
        // Se lanza en un Job separado porque 'collect' en el socket es infinito
        syncJob = viewModelScope.launch {
            try {
                repository.startRealtimeSync(listId)
            } catch (e: Exception) {
                _errorEvents.emit("Fallo en la conexión de tiempo real")
            }
        }

        // 2. OBSERVAR FUENTE DE VERDAD (Room -> UI)
        productsJob = viewModelScope.launch {
            repository.observeProducts(listId).collect { productList ->
                _uiState.update {
                    it.copy(products = productList, isLoading = false)
                }
            }
        }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.createProduct(product)
            } catch (e: Exception) {
                _errorEvents.emit("Error al crear producto")
            }
        }
    }

    fun updateProduct(productId: String, product: Product) {
        viewModelScope.launch {
            try {
                repository.updateProduct(productId, product)
            } catch (e: Exception) {
                _errorEvents.emit("Error al actualizar producto")
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(productId)
            } catch (e: Exception) {
                _errorEvents.emit("No se pudo eliminar el producto")
            }
        }
    }

    // UX OPTIMISTA: El repositorio actualiza Room antes de ir al servidor
    fun updateProductStatus(productId: String, status: ProductStatus) {
        viewModelScope.launch {
            try {
                repository.updateStatus(productId, status)
            } catch (e: Exception) {
                // Si el servidor falla, el error se envía por el SharedFlow
                _errorEvents.emit("Error al sincronizar estado: ${e.message}")
            }
        }
    }
}