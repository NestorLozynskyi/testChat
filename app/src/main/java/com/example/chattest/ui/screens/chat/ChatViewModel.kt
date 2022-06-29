package com.example.chattest.ui.screens.chat

import androidx.lifecycle.MutableLiveData
import com.example.chattest.base.BaseViewModel
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.*
import com.example.chattest.network.Connect
import com.example.chattest.utils.shared.SharedManager

class ChatViewModel(
    private val ws: Connect,
    private val sharedManager: SharedManager
) : BaseViewModel() {

    //override var wsConnect: Connect? = null

    val ldMessage = MutableLiveData<Message>()
    val ldMessages = MutableLiveData<ArrayList<Message>>()

    /*init {
        init()
    }*/

    fun getUserId(): Int = sharedManager.userId ?: -1

    fun getMessage(id: Int) {
        ws.sendRequest(
            Constants.Request.GetChatMessage,
            GetChatRequest(id)
        )
    }
    fun sendMessage(id: Int, message: String) : Boolean {
        return if (ws.sendRequest(
                Constants.Request.SendMessage,
                SendMessageRequest(
                    id.toString(),
                    message
                )
            )
        ) true
        else {
            ws.reconnect(ldReconnect)
            //reconnect()
            false
        }
    }

    /*fun reconnect(){
        GlobalScope.launch (Dispatchers.IO) {
            init()
            ldReconnect.postValue(wsConnect?.reconnect())
        }
    }*/

    fun listenerWS(){
        ws.wsListener({ event, data ->
            when (event) {
                Constants.Response.GetChatMessage -> {
                    val it = ws.convertFromJson(data, GetChatResponse::class.java)
                    ldMessages.postValue(it.data.data.messages)
                }
                Constants.Response.SendMessage, Constants.Response.SendMessageSelf -> {
                    val it = ws.convertFromJson(data, ChatResponse::class.java)
                    //sharedManager.chatId = it.data.data.roomId
                    ldMessage.postValue(it.data.data.message)
                }
            }
        }, {})
    }
}