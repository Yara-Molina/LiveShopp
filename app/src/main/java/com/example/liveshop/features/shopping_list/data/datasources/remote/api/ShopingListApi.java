package com.example.liveshop.features.shopping_list.data.datasources.remote.api;

import com.example.liveshop.features.shopping_list.data.datasources.remote.models.ShoppingListDto;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShopingListApi {
    @GET("lists/")
    suspend fun getLists(): List<ShoppingListDto>

    @POST("lists/")
    suspend fun createList(
            @Body body:Map<String, String>
    ): SharedListDto

    @PUT("lists/{id}")
    suspend fun updateList(
            @Path("id")id: String,
            @Body body: Map<String, String>
    ): SharedListDto

    @DELETE("lists/{id}")
    suspend fun deleteList(
            @Path("id") id: String
    )

}
