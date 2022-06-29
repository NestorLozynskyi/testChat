package com.example.chattest.ui.screens.chat

import android.graphics.Rect
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattest.base.BaseFragment
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.*
import com.example.chattest.databinding.FragmentChatBinding
import com.example.chattest.network.Connect
import com.example.chattest.utils.extensions.bindDataTo
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::class.java) {

    private val wsConnect: Connect by inject()
    private val sharedManager: SharedManager by inject()
    private val gson: Gson by inject()

    private val messages: ArrayList<Message> = arrayListOf()
    private lateinit var adapterMessage: MessageAdapter

    override fun created() {

        val id = arguments?.getInt("[Chat]id")

        if (id != null) {
            getMessage(id)
        }/* else{
            activity?.onBackPressed()
        }*/
        with(binding){
            send.setOnClickListener {
                if (id != null && !editMessage.text.isNullOrBlank()) {
                    sendMessage(id, editMessage.text.toString())
                    editMessage.setText("")
                }
            }
            adapterMessage = MessageAdapter(sharedManager.userId ?: -1, requireContext())
            recycler.adapter = adapterMessage

            parentView.viewTreeObserver.addOnGlobalLayoutListener {
                val r = Rect()
                parentView.getWindowVisibleDisplayFrame(r)
                val screenHeight = parentView.rootView.height
                val keypadHeight = screenHeight - r.bottom
                if (keypadHeight > screenHeight * 0.15) {
                    recycler.layoutManager?.scrollToPosition(messages.lastIndex)
                }
            }
        }

        wsConnect.swListener({ event, data ->
            when(event){
                Constants.Response.GetChatMessage -> {
                    val it = gson.fromJson(data, GetChatResponse::class.java)
                    messages.addAll(it.data.data.messages)
                    adapterMessage.setData(messages)
                    binding.recycler.layoutManager?.scrollToPosition(messages.lastIndex)
                }
                Constants.Response.SendMessage, Constants.Response.SendMessageSelf -> {
                    val it = gson.fromJson(data, ChatResponse::class.java)
                    //sharedManager.chatId = it.data.data.roomId
                    messages.add(it.data.data.message)
                    adapterMessage.setData(messages)
                    binding.recycler.layoutManager?.scrollToPosition(messages.lastIndex)
                }
                /*else -> {
                    println("!!!!!!@!!!!!else $event")
                }*/
            }
        },{})
    }

    /*override fun observe() {
        bindDataTo(viewModel.ld){
            messages.add(it)
            adapterMessage.setData(messages)
            binding.recycler.layoutManager?.scrollToPosition(messages.lastIndex)
        }
        bindDataTo(viewModel.ldList){
            messages.addAll(it)
            adapterMessage.setData(messages)
            binding.recycler.layoutManager?.scrollToPosition(messages.lastIndex)
        }
    }*/


    private fun getMessage(id: Int) {
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.GetChatMessage,
                    GetChatRequest(id)
                )
            )
        )
    }
    private fun sendMessage(id: Int, message: String) {
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
}