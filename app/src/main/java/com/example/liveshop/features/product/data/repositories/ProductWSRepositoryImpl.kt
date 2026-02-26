package com.example.liveshop.features.product.data.repositories

import com.example.liveshop.features.product.data.datasources.remote.api.ProductWSRepository
import com.example.liveshop.features.product.data.datasources.remote.models.ProductResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.*
import okhttp3.WebSocket
import org.json.JSONObject
import javax.inject.Inject

class ProductWSRepositoryImpl @Inject constructor(
    private val client: OkHttpClient
) : ProductWSRepository {

    override fun observeProducts(listId: String): Flow<List<ProductResponse>> = callbackFlow {

        val request = Request.Builder()
            .url("wss://liveshop.duckdns.org/ws/lists/$listId")
            .build()

        val listener = object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                webSocket.send("ping")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val json = JSONObject(text)

                    if (json.getString("type") == "LIST_UPDATED") {
                        val productsArray = json.getJSONArray("products")
                        val list = mutableListOf<ProductResponse>()

                        for (i in 0 until productsArray.length()) {
                            val item = productsArray.getJSONObject(i)
                            list.add(
                                ProductResponse(
                                    id = item.getString("id"),
                                    list_id = item.getString("list_id"),
                                    name = item.getString("name"),
                                    quantity = item.getInt("quantity"),
                                    status = item.getString("status"),
                                    created_at = item.getString("created_at")
                                )
                            )
                        }

                        trySend(list)
                    }

                } catch (_: Exception) { }
            }

            override fun onFailure(
                webSocket: WebSocket,
                t: Throwable,
                response: Response?
            ) {
                close(t)
            }
        }

        val socket = client.newWebSocket(request, listener)

        awaitClose {
            socket.close(1000, null)
        }
    }
}
