package com.example.liveshop.features.product.data.repositories

import android.util.Log
import com.example.liveshop.features.product.data.datasources.remote.api.ProductWSRepository
import com.example.liveshop.features.product.data.datasources.remote.models.ProductResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import javax.inject.Inject

class ProductWSRepositoryImpl @Inject constructor(
    private val client: OkHttpClient
) : ProductWSRepository {

    private val TAG = "ProductWSRepository"

    override fun observeProducts(listId: String): Flow<List<ProductResponse>> = callbackFlow {

        val request = Request.Builder()
            .url("wss://liveshop.duckdns.org/ws/lists/$listId")
            .build()

        val listener = object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket connection opened")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d(TAG, "Message received: $text")
                try {
                    val json = JSONObject(text)
                    val type = json.getString("type")

                    if (type == "INITIAL_STATE" || type == "LIST_UPDATED") {
                        val productsArray = json.getJSONArray("products")
                        val list = mutableListOf<ProductResponse>()

                        for (i in 0 until productsArray.length()) {
                            val item = productsArray.getJSONObject(i)
                            list.add(
                                ProductResponse(
                                    id = item.getString("id"),
                                    list_id = listId, // Use listId from the method parameter
                                    name = item.getString("name"),
                                    quantity = item.getInt("quantity"),
                                    status = item.getString("status"),
                                    created_at = item.optString("created_at", "") // Safely handle missing field
                                )
                            )
                        }

                        trySend(list)
                    }

                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing message", e)
                }
            }

            override fun onFailure(
                webSocket: WebSocket,
                t: Throwable,
                response: Response?
            ) {
                Log.e(TAG, "WebSocket connection failure", t)

                close()
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "WebSocket closing: $code - $reason")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "WebSocket closed: $code - $reason")
            }
        }

        val socket = client.newWebSocket(request, listener)

        awaitClose {
            Log.d(TAG, "Closing WebSocket connection")
            socket.close(1000, null)
        }
    }
}
