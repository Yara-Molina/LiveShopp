package com.example.liveshop.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.liveshop.features.product.presentation.screens.ProductScreen
import com.example.liveshop.features.shopping_list.presentation.screens.DashboardScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Dashboard) {
        composable<Dashboard> {
            DashboardScreen(navController = navController)
        }
        composable<Products> {
            val args = it.toRoute<Products>()
            ProductScreen(listId = args.listId, navController = navController)
        }
    }
}
