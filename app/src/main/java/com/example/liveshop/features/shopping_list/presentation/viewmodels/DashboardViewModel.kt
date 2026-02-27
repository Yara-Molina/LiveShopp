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
import com.example.liveshop.features.shopping_list.domain.usecases.SyncListsUseCase
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
    private val getListsUseCase: GetListsUseCase,
    private val syncListsUseCase: SyncListsUseCase
) : ViewModel() {

    var state by mutableStateOf(DashboardUiState())
        private set

    init {
        observeLists()
        refreshFromApi()
    }

    private fun observeLists() {
        viewModelScope.launch {
            getListsUseCase().collect { lists ->
                state = state.copy(
                    lists = lists,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    fun refreshFromApi() {
        viewModelScope.launch {
            try {

                if (state.lists.isEmpty()) state = state.copy(isLoading = true)
                syncListsUseCase() // Este llama a repository.sync_lists()
            } catch (e: Exception) {
                state = state.copy(error = "Error al sincronizar con el servidor")
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    fun addList(name: String, navigate: (String) -> Unit) {
        if (name.isBlank()) return
        viewModelScope.launch {
            try {
                val createdList = createListUseCase(name)

                navigate(createdList.id)
            } catch (e: Exception) {
                state = state.copy(error = "No se pudo crear la lista")
            }
        }
    }

    fun renameList(id: String, newName: String) {
        viewModelScope.launch {
            try {
                val listToUpdate = ShoppingList(id = id, name = newName, created_at = "")
                updateListUseCase(id, listToUpdate)

            } catch (e: Exception) {
                state = state.copy(error = "Error al actualizar")
            }
        }
    }

    fun removeList(id: String) {
        viewModelScope.launch {
            try {
                deleteUseCase(id)
            } catch (e: Exception) {
                state = state.copy(error = "Error al eliminar")
            }
        }
    }

    fun clearError() {
        state = state.copy(error = null)
    }
}