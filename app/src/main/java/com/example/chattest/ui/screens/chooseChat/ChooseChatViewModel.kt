package com.example.chattest.ui.screens.chooseChat

import androidx.lifecycle.MutableLiveData
import com.example.chattest.base.BaseViewModel
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.ChatsListItem
import com.example.chattest.data.objects.ChatsListItemI
import com.example.chattest.data.objects.JoinChatRequest
import com.example.chattest.data.objects.JoinChatResponse
import com.example.chattest.network.Connect
import com.example.chattest.utils.shared.SharedManager

class ChooseChatViewModel(
    private val ws: Connect,
    private val sharedManager: SharedManager
) : BaseViewModel() {

    //override var wsConnect: Connect? = null

    val ld = MutableLiveData<Int>()

    fun listenerWS() {
        ws.wsListener({ event, data ->
            when (event) {
                Constants.Response.RoomCreate, Constants.Response.RoomJoin -> {
                    val dt = ws.convertFromJson(data, JoinChatResponse::class.java)
                    //sharedManager.chatId = it.data.data.roomId
                    val list = sharedManager.chatList.items
                    list.find { it.idChat == dt.data.data.roomId }.let {
                        if (it == null){
                            list.add(ChatsListItemI(dt.data.data.roomId))
                        }
                    }

                    sharedManager.chatList = ChatsListItem(list)
                    ld.postValue(dt.data.data.roomId)//goToChat()
                }
            }
        }, {})
    }

    fun getChats(): ArrayList<ChatsListItemI> = sharedManager.chatList.items

    fun joinChat(id: String){
        ws.sendRequest(
            Constants.Request.RoomJoin,
            JoinChatRequest(id)
        )
    }

    fun createNewChat(){
        ws.sendRequest(
            Constants.Request.RoomCreate,
            ""
        )
    }

    fun delToken() {
        sharedManager.token = ""
    }
}