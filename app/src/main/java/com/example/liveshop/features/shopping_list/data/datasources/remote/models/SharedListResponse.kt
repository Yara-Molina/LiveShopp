package com.example.liveshop.features.shopping_list.data.datasources.remote.models

data class SharedListResponse(
    val id: String,
    val name: String,
    val created_at: String? = null
)