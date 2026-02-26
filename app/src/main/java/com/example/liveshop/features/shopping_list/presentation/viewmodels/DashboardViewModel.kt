package com.example.liveshop.features.shopping_list.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveshop.features.shopping_list.domain.usecases.CreateListUseCase
import com.example.liveshop.features.shopping_list.domain.usecases.DeleteUseCase
import com.example.liveshop.features.shopping_list.domain.usecases.GetListByIdUseCase
import com.example.liveshop.features.shopping_list.domain.usecases.GetShopingListUseCase
import com.example.liveshop.features.shopping_list.domain.usecases.UpdateListUseCase
import com.example.liveshop.features.shopping_list.presentation.screens.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getShoppingListsUseCase: GetShopingListUseCase,
    private val createListUseCase: CreateListUseCase,
    private val deleteListUseCase: DeleteUseCase,
    private val updateListUseCase: UpdateListUseCase,
    private val getListByIdUseCase: GetListByIdUseCase
) : ViewModel() {

    var state by mutableStateOf(DashboardUiState())
        private set

    init {
        observeLists()
    }

    private fun observeLists() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            getShoppingListsUseCase()
                .catch { e ->
                    state = state.copy(isLoading = false, error = e.message)
                }
                .collect { lists ->
                    state = state.copy(isLoading = false, lists = lists, error = null)
                }
        }
    }

    fun addList(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            try {
                createListUseCase(name)
            } catch (e: Exception) {
                state = state.copy(error = "Error al crear")
            }
        }
    }


    fun renameList(id: String, newName: String) {
        viewModelScope.launch {
            try {
                updateListUseCase(id, newName)
            } catch (e: Exception) {
                state = state.copy(error = "Error al actualizar")
            }
        }
    }

    fun removeList(id: String) {
        viewModelScope.launch {
            try {
                deleteListUseCase(id)
            } catch (e: Exception) {
                state = state.copy(error = "Error al eliminar")
            }
        }
    }


    fun selectList(id: String) {
        viewModelScope.launch {
            try {
                val list = getListByIdUseCase(id)

            } catch (e: Exception) {
                state = state.copy(error = "No se encontró la lista")
            }
        }
    }
}