package com.example.liveshop.features.shopping_list.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.liveshop.core.navigation.Products
import com.example.liveshop.features.shopping_list.domain.entities.ShoppingList
import com.example.liveshop.features.shopping_list.presentation.components.ShoppingListCard
import com.example.liveshop.features.shopping_list.presentation.viewmodels.DashboardViewModel

// ── Paleta índigo ──────────────────────────────────────────────
private val Indigo900  = Color(0xFF1E1B4B)
private val Indigo700  = Color(0xFF3730A3)
private val Indigo500  = Color(0xFF6366F1)
private val Indigo300  = Color(0xFFA5B4FC)
private val IndigoSoft = Color(0xFFEEF2FF)
private val Surface    = Color(0xFFF8F7FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val pullToRefreshState = rememberPullToRefreshState()

    var showDialog     by remember { mutableStateOf(false) }
    var newListName    by remember { mutableStateOf("") }
    var showEditDialog by remember { mutableStateOf(false) }
    var listToEdit     by remember { mutableStateOf<ShoppingList?>(null) }
    var editedName     by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Surface,
        topBar = {
            // Header con gradiente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(listOf(Indigo900, Indigo700))
                    )
                    .padding(horizontal = 20.dp, vertical = 28.dp)
                    .padding(top = 16.dp)
            ) {
                Column {
                    Text(
                        text = "LiveShop",
                        color = Indigo300,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 3.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Mis Listas",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Indigo500,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(6.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Lista")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = state.isLoading,
            state = pullToRefreshState,
            onRefresh = { viewModel.refreshFromApi() },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.lists.isEmpty() && !state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🛒", fontSize = 48.sp)
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "No tienes listas aún",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Indigo300,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            "Toca + para crear una",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
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

    // ── Diálogo: Nueva lista ───────────────────────────────────
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp),
            title = {
                Text(
                    "Nueva Lista",
                    fontWeight = FontWeight.Bold,
                    color = Indigo900
                )
            },
            text = {
                OutlinedTextField(
                    value = newListName,
                    onValueChange = { newListName = it },
                    label = { Text("Nombre de la lista") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Indigo500,
                        focusedLabelColor = Indigo500,
                        cursorColor = Indigo500
                    )
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
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo500),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Crear")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar", color = Indigo500)
                }
            }
        )
    }

    // ── Diálogo: Editar lista ──────────────────────────────────
    if (showEditDialog) {
        listToEdit?.let { list ->
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                containerColor = Color.White,
                shape = RoundedCornerShape(20.dp),
                title = {
                    Text(
                        "Editar nombre",
                        fontWeight = FontWeight.Bold,
                        color = Indigo900
                    )
                },
                text = {
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        label = { Text("Nuevo nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Indigo500,
                            focusedLabelColor = Indigo500,
                            cursorColor = Indigo500
                        )
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.renameList(list.id, editedName)
                            showEditDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo500),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditDialog = false }) {
                        Text("Cancelar", color = Indigo500)
                    }
                }
            )
        }
    }

    // ── Diálogo: Error ─────────────────────────────────────────
    state.error?.let { error ->
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp),
            title = { Text("Error", fontWeight = FontWeight.Bold, color = Indigo900) },
            text = { Text(error, color = Color.Gray) },
            confirmButton = {
                Button(
                    onClick = { viewModel.clearError() },
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo500),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("OK")
                }
            }
        )
    }
}