package com.example.chattest.ui.screens.chooseChat

import android.os.Bundle
import com.example.chattest.R
import com.example.chattest.base.BaseFragment
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.JoinChatRequest
import com.example.chattest.data.objects.JoinChatResponse
import com.example.chattest.data.objects.Request
import com.example.chattest.databinding.FragmentChooseChatBinding
import com.example.chattest.network.Connect
import com.example.chattest.utils.extensions.bindDataTo
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseChatFragment : BaseFragment<FragmentChooseChatBinding>(FragmentChooseChatBinding::class.java) {

    private val wsConnect: Connect by inject()
    private val sharedManager: SharedManager by inject()
    private val gson: Gson by inject()

    override fun created() {

        with(binding){
            newChat.setOnClickListener { createNewChat() }
            joinChat.setOnClickListener { joinChat(editJoinChat.text.toString()) }
            exit.setOnClickListener { exitFromAccount() }
        }

        wsConnect.swListener({ event, data ->
            when(event){
                Constants.Response.RoomCreate, Constants.Response.RoomJoin -> {
                    val it = gson.fromJson(data, JoinChatResponse::class.java)
                    //sharedManager.chatId = it.data.data.roomId
                    goToChat(it.data.data.roomId)
                }
            }
        },{})
    }

    /*override fun observe() {
        bindDataTo(viewModel.ld){
            if (it != null){
                viewModel.ld.postValue(null)
                goToChat(it)
            }
        }
    }*/

    private fun goToChat(chatId: Int){
        try {
            val bundle = Bundle()
            bundle.putInt("[Chat]id", chatId)
            navController?.navigate(R.id.action_chooseChatFragment_to_chatFragment, bundle)
        } catch (e: Exception){}
    }


    private fun createNewChat(){
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.RoomCreate,
                    ""
                )
            )
        )
    }

    private fun joinChat(id: String){
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.RoomJoin,
                    JoinChatRequest(id)
                )
            )
        )
    }

    private fun exitFromAccount(){
        sharedManager.token = ""
        navController?.navigate(R.id.action_chooseChatFragment_to_authorizationFragment)
    }
}