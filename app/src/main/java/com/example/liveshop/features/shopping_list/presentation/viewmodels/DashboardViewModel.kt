package com.example.liveshop.features.shopping_list.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.domain.usecases.CreateListUseCase
import com.example.liveshop.features.shopping_list.domain.usecases.DeleteUseCase
import com.example.liveshop.features.shopping_list.domain.usecases.GetListsUseCase
import com.example.liveshop.features.shopping_list.domain.usecases.UpdateListUseCase
import com.example.liveshop.features.shopping_list.presentation.screens.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val createListUseCase: CreateListUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val updateListUseCase: UpdateListUseCase,
    private val getListsUseCase: GetListsUseCase
) : ViewModel() {

    var state by mutableStateOf(DashboardUiState())
        private set

    init {
        observeLists()
    }

    private fun observeLists() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val lists = getListsUseCase()
                state = state.copy(isLoading = false, lists = lists, error = null)
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun addList(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            try {
                // We can generate a temporary id for the sake of the example
                val newList = ShoppingList(id = "", name = name, created_at = "")
                createListUseCase(newList)
                observeLists() // Refresh the list
            } catch (e: Exception) {
                state = state.copy(error = "Error al crear")
            }
        }
    }

    fun clearError() {
        state = state.copy(error = null)
    }

    fun renameList(id: String, newName: String) {
        viewModelScope.launch {
            try {
                val listToUpdate = ShoppingList(id = id, name = newName, created_at = "")
                updateListUseCase(id, listToUpdate)
                observeLists() // Refresh the list
            } catch (e: Exception) {
                state = state.copy(error = "Error al actualizar")
            }
        }
    }

    fun removeList(id: String) {
        viewModelScope.launch {
            try {
                deleteUseCase(id)
                observeLists() // Refresh the list
            } catch (e: Exception) {
                state = state.copy(error = "Error al eliminar")
            }
        }
    }
}