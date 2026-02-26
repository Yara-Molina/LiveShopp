package com.example.liveshop.features.product.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveshop.features.product.domain.entities.Product
import com.example.liveshop.features.product.domain.entities.ProductStatus
import com.example.liveshop.features.product.domain.usecases.CreateProduct
import com.example.liveshop.features.product.domain.usecases.DeleteProduct
import com.example.liveshop.features.product.domain.usecases.ObserveProducts
import com.example.liveshop.features.product.domain.usecases.UpdateProduct
import com.example.liveshop.features.product.domain.usecases.UpdateStatus
import com.example.liveshop.features.product.presentation.screens.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val observeProductsUC: ObserveProducts,
    private val createUC: CreateProduct,
    private val updateStatusUC: UpdateStatus,
    private val updateProductUC: UpdateProduct,
    private val deleteUC: DeleteProduct
) : ViewModel() {

    private val listId = MutableStateFlow<String?>(null)

    val uiState: StateFlow<ProductUIState> =
        listId
            .filterNotNull()
            .flatMapLatest { id ->
                observeProductsUC(id)
            }
            .map { products ->
                ProductUIState(
                    listId = listId.value ?: "",
                    isLoading = false,
                    products = products
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                ProductUIState(isLoading = true)
            )

    fun setList(id: String) {
        listId.value = id
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            createUC(product)
        }
    }

    fun updateStatus(productId: String, status: ProductStatus) {
        viewModelScope.launch {
            updateStatusUC(productId, status)
        }
    }

    fun updateProduct(productId: String, product: Product) {
        viewModelScope.launch {
            updateProductUC(productId, product)
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            deleteUC(productId)
        }
    }
}