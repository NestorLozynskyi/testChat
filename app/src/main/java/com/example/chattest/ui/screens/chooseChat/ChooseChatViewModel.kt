package com.example.chattest.ui.screens.chooseChat

import androidx.lifecycle.MutableLiveData
import com.example.chattest.base.BaseViewModel
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.JoinChatRequest
import com.example.chattest.data.objects.JoinChatResponse
import com.example.chattest.data.objects.Request
import com.example.chattest.network.Connect
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson

class ChooseChatViewModel(
    private val wsConnect: Connect,
    private val gson: Gson,
    sharedManager: SharedManager
) : BaseViewModel() {

    val ld = MutableLiveData<Int>()

    init {
        wsConnect.swListener({ event, data ->
            when(event){
                Constants.Response.RoomCreate, Constants.Response.RoomJoin -> {
                    val it = gson.fromJson(data, JoinChatResponse::class.java)
                    //sharedManager.chatId = it.data.data.roomId
                    ld.postValue(it.data.data.roomId)
                }
            }
        },{})
    }

    fun createNewChat(){
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.RoomCreate,
                    ""
                )
            )
        )
    }

    fun joinChat(id: String){
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.RoomJoin,
                    JoinChatRequest(id)
                )
            )
        )
    }
}