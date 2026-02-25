package com.example.liveshop.features.shopping_list.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveshop.features.shopping_list.domain.usecases.GetListsUseCase
import com.example.liveshop.features.shopping_list.presentation.screens.DashboardUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getListsUseCase: GetListsUseCase, // Sigue el patrón de tu imagen

) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUIState>(DashboardUIState.Loading)
    val uiState: StateFlow<DashboardUIState> = _uiState.asStateFlow()

    init {
        loadLists()
    }

    private fun loadLists() {
        viewModelScope.launch {

            getListsUseCase().collect { lists ->
                _uiState.value = DashboardUIState.Success(lists)
            }
        }
    }
}