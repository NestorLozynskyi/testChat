package com.example.chattest.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val showError: MutableLiveData<String> = MutableLiveData()
    val ldReconnect = MutableLiveData<Boolean>()
    //val connect: MutableLiveData<Boolean> = MutableLiveData()
    //val ldWebSocket : MutableLiveData<WebSocket> = MutableLiveData()

    //abstract val wsConnect: Connect?

    /*fun reconnect(){
        GlobalScope.launch (Dispatchers.IO) {
            createNewConnect()
            //connect.postValue(true)
        }
    }*/

    //private var coroutineHelper = CoroutineHelper(viewModelScope)

    /*protected open fun launch(
        onError: (e: Throwable) -> Unit,
        coroutineContext: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return coroutineHelper.launch(coroutineContext, block, onError)
    }*/

    open fun onErrorHandler(throwable: Throwable) {
    }

    //private var factory = WebSocketFactory()
    //var webSockets: WebSocket? = null

    /*fun createNewConnect(): Boolean{

        //webSockets =

        return connect(factory.createSocket(Constants.socketChatApi))
    }

    private fun connect(ws: WebSocket): Boolean{
        return try {
            ldWebSocket.postValue(ws.connect())
            println("connect (30)")
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

        ldWebSocket.value?.addListener(listener({ event, data ->
            listener.invoke(event, data)
        }, {
            error.invoke(it)
        }))
    }

    fun sendRequest(event: String, data: Any?) : Boolean{
        return if (ldWebSocket.value?.state == WebSocketState.OPEN) {
            ldWebSocket.value?.sendText(
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

    fun <T> convertFromJson(data: String, cl: Class<T>): T = gson.fromJson(data, cl)*/
}