package com.example.chattest.network

import androidx.lifecycle.MutableLiveData
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
    private var tryReconnect = 2

    fun createNewConnect(): Boolean?{

        ws = factory.createSocket(Constants.socketChatApi)

        return connect()
    }

    private fun listener(listener:(event: String, data: String) -> Unit, error:(String) -> Unit) =
        object : WebSocketAdapter() {
            @Throws(Exception::class)
            override fun onTextMessage(websocket: WebSocket, message: String) {
                println("onTextMessage: websocket: $websocket message: $message")

                try {
                    val response = gson.fromJson(message, Response::class.java)
                    if (response != null){
                        with(response.data){
                            if (meta.status == 200){
                                listener.invoke(response.event, message)
                            } else {
                                error.invoke(meta.message ?: "Error")
                            }
                        }
                    } else {
                        error.invoke("Response is empty")
                    }
                } catch (e: Exception){
                    error.invoke("###Error: $e")
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

            override fun onDisconnected(
                websocket: WebSocket?,
                serverCloseFrame: WebSocketFrame?,
                clientCloseFrame: WebSocketFrame?,
                closedByServer: Boolean
            ) {
                super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer)
                println("onDisconnected: websocket $websocket serverCloseFrame $serverCloseFrame clientCloseFrame $clientCloseFrame closedByServer $closedByServer")
            }

            override fun onError(websocket: WebSocket?, cause: WebSocketException?) {
                super.onError(websocket, cause)
                println("onError websocket $websocket cause $cause")
            }
        }

    fun wsListener(listener:(event: String, data: String) -> Unit, error:(String) -> Unit) {

        ws?.addListener(listener({ event, data ->
            listener.invoke(event, data)
        }, {
            error.invoke(it)
        }))
    }

    fun sendRequest(event: String, data: Any?) : Boolean{
        return if (ws?.state == WebSocketState.OPEN) {
            ws?.sendText(
                gson.toJson(
                    Request(
                        event,
                        data ?: ""
                    )
                )
            )
            true
        } else false
    }

    private fun connect(): Boolean?{
        return try {
            ws?.connect()
            println("connect (30)")
            //isConnect = true
            //ws
            true
        } catch (e: OpeningHandshakeException) {
            println("Error (31): $e")
            false
        } catch (e: HostnameUnverifiedException) {
            println("Error (32): $e")
            false
        } catch (e: WebSocketException) {
            println("Error (33): $e")
            false
        } catch (e: Exception) {
            println("Error (34): $e")
            false
        }
    }

    fun reconnect(mld: MutableLiveData<Boolean>){
        GlobalScope.launch (Dispatchers.IO) {
            createNewConnect()
            mld.postValue(true)
        }
    }

    fun <T> convertFromJson(data: String, cl: Class<T>): T = gson.fromJson(data, cl)

    /*fun getMessage(id: Int, webSocket: WebSocket?) {
        sendRequest(
            Constants.Request.GetChatMessage,
            GetChatRequest(id),
            webSocket
        )
    }*/
    /*fun sendMessage(id: Int, message: String) : Boolean {
            return sendRequest(
                    Constants.Request.SendMessage,
                    SendMessageRequest(
                        id.toString(),
                        message
                    )
                )
    }*/
}