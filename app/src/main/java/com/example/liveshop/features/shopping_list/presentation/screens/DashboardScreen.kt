package com.example.liveshop.features.shopping_list.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import androidx.navigation.NavController
import com.example.liveshop.core.navigation.Products
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.presentation.components.ShoppingListCard
import com.example.liveshop.features.shopping_list.presentation.viewmodels.DashboardViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val pullToRefreshState = rememberPullToRefreshState()

    var showDialog by remember { mutableStateOf(false) }
    var newListName by remember { mutableStateOf("") }
    var showEditDialog by remember { mutableStateOf(false) }
    var listToEdit by remember { mutableStateOf<ShoppingList?>(null) }
    var editedName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.observeLists()
    }

    Scaffold(
        topBar = {
            Text(
                "Mis Listas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 24.dp),
                fontWeight = FontWeight.Bold
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF7C3AED),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Lista")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = state.isLoading,
            state = pullToRefreshState,
            onRefresh = { viewModel.observeLists() },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.lists.isEmpty() && !state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No tienes listas aún",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = state.lists,
                    key = { it.id }
                ) { list ->
                    ShoppingListCard(
                        list = list,
                        onDelete = { viewModel.removeList(list.id) },
                        onEdit = {
                            listToEdit = list
                            editedName = list.name
                            showEditDialog = true
                        },
                        onClick = {
                            Log.d("UUID_CHECK", "Navigating with listId: ${list.id}")
                            navController.navigate(Products(list.id))
                        }
                    )
                }
            }
        }
    }



    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nueva Lista") },
            text = {
                OutlinedTextField(
                    value = newListName,
                    onValueChange = { newListName = it },
                    label = { Text("Nombre de la lista") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addList(newListName) { listId ->
                            navController.navigate(Products(listId))
                        }
                        newListName = ""
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
                ) {
                    Text("Crear")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showEditDialog) {
        listToEdit?.let { list ->
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text("Editar nombre") },
                text = {
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Nuevo nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.renameList(list.id, editedName)
                            showEditDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }

    state.error?.let { error ->
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Error") },
            text = { Text(error) },
            confirmButton = {
                Button(onClick = { viewModel.clearError() }) {
                    Text("OK")
                }
            }
        )
    }
}