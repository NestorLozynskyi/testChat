package com.example.chattest.ui.screens.chat

import androidx.lifecycle.MutableLiveData
import com.example.chattest.base.BaseViewModel
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.*
import com.example.chattest.network.Connect
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson

class ChatViewModel(
    private val wsConnect: Connect,
    private val sharedManager: SharedManager,
    private val gson: Gson
) : BaseViewModel() {

    val ld = MutableLiveData<Message>()
    val ldList = MutableLiveData<ArrayList<Message>>()

    init{
        wsConnect.swListener({ event, data ->
            when(event){
                Constants.Response.GetChatMessage -> {
                    val it = gson.fromJson(data, GetChatResponse::class.java)
                    ldList.postValue(it.data.data.messages)
                }
                Constants.Response.SendMessage, Constants.Response.SendMessageSelf -> {
                    val it = gson.fromJson(data, ChatResponse::class.java)
                    //sharedManager.chatId = it.data.data.roomId
                    ld.postValue(it.data.data.message)
                }
                else -> {
                    println("!!!!!!@!!!!!else $event")
                }
            }
        },{})
    }

    fun getMessage(id: Int) {
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.GetChatMessage,
                    GetChatRequest(id)
                )
            )
        )
    }
    fun sendMessage(id: Int, message: String) {
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.SendMessage,
                    SendMessageRequest(
                        id.toString(),
                        message
                    )
                )
            )
        )
    }

    fun getUserId() = sharedManager.userId
}