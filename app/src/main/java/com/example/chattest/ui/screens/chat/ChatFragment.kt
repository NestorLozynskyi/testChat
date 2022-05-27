package com.example.chattest.ui.screens.chat

import com.example.chattest.base.BaseFragment
import com.example.chattest.data.objects.Message
import com.example.chattest.databinding.FragmentChatBinding
import com.example.chattest.utils.extensions.bindDataTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::class.java) {
    override val viewModel: ChatViewModel by viewModel()

    private val messages: ArrayList<Message> = arrayListOf()
    private lateinit var adapterMessage: MessageAdapter

    override fun created() {

        val id = arguments?.getInt("[Chat]id")

        if (id != null) {
            viewModel.getMessage(id)
        }/* else{
            activity?.onBackPressed()
        }*/
        with(binding){
            send.setOnClickListener {
                if (id != null && !editMessage.text.isNullOrBlank()) {
                    viewModel.sendMessage(id, editMessage.text.toString())
                    editMessage.setText("")
                }
            }
            adapterMessage = MessageAdapter(viewModel.getUserId() ?: -1)
            recycler.adapter = adapterMessage
        }
    }

    override fun observe() {
        bindDataTo(viewModel.ld){
            messages.add(it)
            adapterMessage.setData(messages)
        }
        bindDataTo(viewModel.ldList){
            messages.addAll(it)
            adapterMessage.setData(messages)
        }
    }
}