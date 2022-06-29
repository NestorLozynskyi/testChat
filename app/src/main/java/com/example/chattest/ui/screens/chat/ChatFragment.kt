package com.example.chattest.ui.screens.chat

import android.graphics.Rect
import com.example.chattest.base.BaseFragment
import com.example.chattest.data.objects.*
import com.example.chattest.databinding.FragmentChatBinding
import com.example.chattest.ui.main.MainActivity
import com.example.chattest.utils.extensions.bindDataTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::class.java) {

    override val viewModel: ChatViewModel by viewModel()

    private val messages: ArrayList<Message> = arrayListOf()
    private lateinit var adapterMessage: MessageAdapter


    private var openKeyboard = false

    private var idChat: Int? = null

    override fun onStart() {
        super.onStart()
        viewModel.listenerWS()
        viewModel.getMessage(idChat!!)

        with(binding){
            send.setOnClickListener {
                if (idChat != null && !editMessage.text.isNullOrBlank()) {
                    if (viewModel.sendMessage(idChat!!, editMessage.text.toString()))
                        editMessage.setText("")
                    else {
                        //(activity as MainActivity).reconnect()
                        toast("Don`t send")
                    }
                }
            }

        }
    }

    override fun created() {

        //viewModel.wsConnect = (activity as MainActivity).ws

        idChat = arguments?.getInt("[Chat]id")

        //viewModel.init()
        /* else{
            activity?.onBackPressed()
        }*/
        with(binding){
            adapterMessage = MessageAdapter(viewModel.getUserId(), requireContext())
            recycler.adapter = adapterMessage

            parentView.viewTreeObserver.addOnGlobalLayoutListener {
                val r = Rect()
                parentView.getWindowVisibleDisplayFrame(r)
                val screenHeight = parentView.rootView.height
                val keypadHeight = screenHeight - r.bottom
                openKeyboard = if (keypadHeight > screenHeight * 0.15) {
                    if (!openKeyboard) {
                        recycler.layoutManager?.scrollToPosition(messages.lastIndex)
                    }
                    true
                } else false
            }
            chatData.setOnClickListener {
                //viewModel.reconnect()
                toast("reconnect")
            }
        }
    }

    override fun observe() {
        bindDataTo(viewModel.ldMessage){
            if (it.chatId == idChat)
                messages.add(it)
            adapterMessage.setData(messages)
            binding.recycler.layoutManager?.scrollToPosition(messages.lastIndex)
        }
        bindDataTo(viewModel.ldMessages){
            messages.addAll(it)
            adapterMessage.setData(messages)
            binding.recycler.layoutManager?.scrollToPosition(messages.lastIndex)
        }
        /*bindDataTo(viewModel.ldWebSocket){
            viewModel.listenerWS()
            if (idChat != null) {
                viewModel.getMessage(idChat!!)
            }
        }*/
    }
}