package com.example.liveshop.features.product.data.datasources.remote.api

import com.example.liveshop.features.product.data.datasources.remote.models.ProductRequest
import com.example.liveshop.features.product.data.datasources.remote.models.ProductResponse
import com.example.liveshop.features.product.data.datasources.remote.models.UpdateStatusRequest
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.Query

interface ProductHTTPApi {

    @POST("products/")
    suspend fun createProduct(
        @Body body: ProductRequest
    ): ProductResponse

    @PUT("products/{productId}")
    suspend fun updateProduct(
        @Path("productId") productId: String,
        @Body body: ProductRequest
    ): ProductResponse

    @PATCH("products/{id}/status")
    suspend fun updateStatus(
        @Path("id") id: String,
        @Query("status") status: String
    ): Response<Unit>

    @DELETE("products/{productId}")
    suspend fun deleteProduct(
        @Path("productId") productId: String
    )
}
