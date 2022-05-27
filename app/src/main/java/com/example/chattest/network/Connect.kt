package com.example.chattest.network

import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.*
import com.google.gson.Gson
import com.neovisionaries.ws.client.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Connect(private val gson: Gson) {

    private var factory = WebSocketFactory()
    var ws: WebSocket? = null

    fun createConnect(){
        ws = factory.createSocket(Constants.socketChatApi)
        GlobalScope.launch (Dispatchers.IO) {
            try {
                ws?.connect()
                println("connect (30)")
            } catch (e: OpeningHandshakeException) {
                println("Error (31): $e")
            } catch (e: HostnameUnverifiedException) {
                println("Error (32): $e")
            } catch (e: WebSocketException) {
                println("Error (33): $e")
            } catch (e: Exception) {
                println("Error (34): $e")
            }
        }
    }

    fun swListener(listener:(event: String, data: String) -> Unit, error:(String) -> Unit){
        ws?.addListener(object : WebSocketAdapter() {
            @Throws(Exception::class)
            override fun onTextMessage(websocket: WebSocket, message: String) {
                println("onTextMessage: websocket: $websocket message: $message")

                try {
                    val response = gson.fromJson(message, Response::class.java)
                    if (response != null){
                        with(response.data){
                            if (meta.status == 200){
                                listener.invoke(response.event, message)
                                /*when(response.event) {
                                    Constants.AUTH_LOGIN_RESPONSE -> {
                                        listener.invoke(
                                            gson.fromJson(
                                                message,
                                                AuthResponse::class.java
                                            )
                                        )
                                    }
                                    Constants.AUTH_TOKEN_RESPONSE -> {
                                        listener.invoke(
                                            gson.fromJson(
                                                message,
                                                TokenAuthResponse::class.java
                                            )
                                        )
                                    }
                                    Constants.REG_NEW_ACCOUNT_RESPONSE -> {
                                        listener.invoke(true)
                                    }
                                    Constants.ROOM_CREATE_RESPONSE, Constants.ROOM_JOIN_RESPONSE -> {
                                        listener.invoke(
                                            gson.fromJson(
                                                message,
                                                JoinChatResponse::class.java
                                            )
                                        )
                                    }
                                    else -> {
                                        error.invoke(meta.message ?: "Error")
                                    }
                                }*/
                            } else {
                                error.invoke(meta.message ?: "Error")
                            }
                        }
                    } else {
                        error.invoke("Response is empty")
                    }
                } catch (e: Exception){
                    error.invoke("Error: $e")
                }
            }

            override fun onConnected(
                websocket: WebSocket?,
                headers: MutableMap<String, MutableList<String>>?
            ) {
                super.onConnected(websocket, headers)
                println("onConnected: websocket: $websocket headers: $headers")
            }

            override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
                super.onConnectError(websocket, exception)
                println("onConnectError: websocket: $websocket exception: $exception")
            }
        })
    }
}