package com.example.liveshop.features.shopping_list.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveshop.features.shopping_list.domain.usecases.GetListsUseCase
import com.example.liveshop.features.shopping_list.presentation.screens.DashboardUIState
import com.example.liveshop.features.shopping_list.presentation.screens.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getLists: GetSharedListsUseCase,
    private val createList: CreateSharedListUseCase,
    private val deleteList: DeleteSharedListUseCase
) : ViewModel() {

    var state by mutableStateOf(DashboardUiState())
        private set

    init {
        loadLists()
    }

    fun loadLists() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val lists = getLists()
                state = state.copy(
                    isLoading = false,
                    lists = lists
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun addList(name: String) {
        viewModelScope.launch {
            createList(name)
            loadLists()
        }
    }

    fun removeList(id: String) {
        viewModelScope.launch {
            deleteList(id)
            loadLists()
        }
    }
}