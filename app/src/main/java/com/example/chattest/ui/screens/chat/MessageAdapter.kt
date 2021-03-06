package com.example.chattest.ui.screens.chat

import android.content.Context
import com.example.chattest.R
import com.example.chattest.base.BaseAdapter
import com.example.chattest.base.ViewHolder
import com.example.chattest.data.objects.Message
import com.example.chattest.databinding.ItemChatBinding
import com.example.chattest.utils.extensions.gone
import com.example.chattest.utils.extensions.visible

class MessageAdapter(private val uId: Int, private val cont: Context) : BaseAdapter<Message>(R.layout.item_chat) {
    override fun bindViewHolder(holder: ViewHolder, data: Message) {
        holder.itemView.apply {
            val binding = ItemChatBinding.bind(this)
            data.apply {
                with(binding) {
                    if (userId == uId){
                        tvMy.text = message
                        llInterlocutor.gone()
                        llMy.visible()
                    } else {
                        tvInterlocutor.text = message
                        tvInterlocutorName.text = cont.resources.getString(R.string.user_id, userId)
                        llInterlocutor.visible()
                        llMy.gone()
                    }
                }
            }
        }
    }
}