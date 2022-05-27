package com.example.chattest.ui.screens.chooseChat

import android.os.Bundle
import com.example.chattest.R
import com.example.chattest.base.BaseFragment
import com.example.chattest.databinding.FragmentChooseChatBinding
import com.example.chattest.utils.extensions.bindDataTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseChatFragment : BaseFragment<FragmentChooseChatBinding>(FragmentChooseChatBinding::class.java) {

    override val viewModel: ChooseChatViewModel by viewModel()

    override fun created() {

        with(binding){
            newChat.setOnClickListener { viewModel.createNewChat() }
            joinChat.setOnClickListener { viewModel.joinChat(editJoinChat.text.toString()) }
        }
    }

    override fun observe() {
        bindDataTo(viewModel.ld){
            if (it != null){
                viewModel.ld.postValue(null)
                goToChat(it)
            }
        }
    }

    private fun goToChat(chatId: Int){
        try {
            val bundle = Bundle()
            bundle.putInt("[Chat]id", chatId)
            navController?.navigate(R.id.action_chooseChatFragment_to_chatFragment, bundle)
        } catch (e: Exception){}
    }
}