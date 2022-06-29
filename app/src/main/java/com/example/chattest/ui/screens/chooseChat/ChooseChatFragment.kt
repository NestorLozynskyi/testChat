package com.example.chattest.ui.screens.chooseChat

import android.os.Bundle
import com.example.chattest.R
import com.example.chattest.base.BaseFragment
import com.example.chattest.databinding.FragmentChooseChatBinding
import com.example.chattest.ui.main.MainActivity
import com.example.chattest.utils.extensions.bindDataTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseChatFragment : BaseFragment<FragmentChooseChatBinding>(FragmentChooseChatBinding::class.java) {

    override val viewModel: ChooseChatViewModel by viewModel()

    private lateinit var chatsListAdapter: ChatListAdapter

    override fun onStart() {
        super.onStart()
        viewModel.listenerWS()
    }

    override fun created() {

        //viewModel.wsConnect = (activity as MainActivity).ws

        //viewModel.init()

        with(binding){
            newChat.setOnClickListener { viewModel.createNewChat() }
            joinChat.setOnClickListener { viewModel.joinChat(editJoinChat.text.toString()) }
            exit.setOnClickListener { exitFromAccount() }

            chatsListAdapter = ChatListAdapter{
                viewModel.joinChat(it.toString())
            }
            recycler.adapter = chatsListAdapter
            chatsListAdapter.setData(viewModel.getChats())
        }
    }

    override fun observe() {
        bindDataTo(viewModel.ld){
            viewModel.ld.postValue(null)
            try {
                val bundle = Bundle()
                bundle.putInt("[Chat]id", it)
                navController?.navigate(R.id.action_chooseChatFragment_to_chatFragment, bundle)
            } catch (e: Exception){}
        }
        /*bindDataTo(viewModel.ldWebSocket){
            viewModel.listenerWS()
        }*/
    }

    private fun exitFromAccount(){
        viewModel.delToken()
        navController?.navigate(R.id.action_chooseChatFragment_to_authorizationFragment)
    }
}