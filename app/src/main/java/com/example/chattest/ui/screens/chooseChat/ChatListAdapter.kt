package com.example.chattest.ui.screens.chooseChat

import com.example.chattest.R
import com.example.chattest.base.BaseAdapter
import com.example.chattest.base.ViewHolder
import com.example.chattest.data.objects.ChatsListItemI
import com.example.chattest.databinding.ItemChatListBinding

class ChatListAdapter(private val listener: (Int) -> Unit) : BaseAdapter<ChatsListItemI>(R.layout.item_chat_list) {
    override fun bindViewHolder(holder: ViewHolder, data: ChatsListItemI) {
        holder.itemView.apply {
            val binding = ItemChatListBinding.bind(this)
            data.apply {
                with(binding) {
                    chatId.text = idChat.toString()
                    parent.setOnClickListener {
                        listener.invoke(idChat)
                    }
                }
            }
        }
    }
}