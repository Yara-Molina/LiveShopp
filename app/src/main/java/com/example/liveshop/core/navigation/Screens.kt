package com.example.liveshop.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Dashboard

@Serializable
data class Products(val listId: String)
