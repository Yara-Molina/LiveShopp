package com.example.liveshop.features.product.data.datasources.remote.api

import com.example.liveshop.features.product.data.datasources.remote.models.ProductRequest
import com.example.liveshop.features.product.data.datasources.remote.models.ProductResponse
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

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

    @PATCH("products/{productId}/status")
    suspend fun updateStatus(
        @Path("productId") productId: String,
        @Body status: String
    ): ProductResponse

    @DELETE("products/{productId}")
    suspend fun deleteProduct(
        @Path("productId") productId: String
    )
}
