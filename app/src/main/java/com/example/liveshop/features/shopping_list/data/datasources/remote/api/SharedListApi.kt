package com.example.liveshop.features.shopping_list.data.datasources.remote.api

import com.example.liveshop.features.shopping_list.data.datasources.remote.models.SharedListCreate
import com.example.liveshop.features.shopping_list.data.datasources.remote.models.SharedListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface SharedListApi {

    @GET("lists/")
    suspend fun getLists(): List<SharedListResponse>

    @POST("lists/")
    suspend fun createList(
        @Body body: SharedListCreate
    ): SharedListResponse

    @PUT("lists/{list_id}")
    suspend fun updateList(
        @Path("list_id") listId: String,
        @Body body: SharedListCreate
    ): SharedListResponse

    @DELETE("lists/{list_id}")
    suspend fun deleteList(
        @Path("list_id") listId: String
    )
}