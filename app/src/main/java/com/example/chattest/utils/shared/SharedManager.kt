package com.example.chattest.utils.shared

import android.content.SharedPreferences
import com.example.chattest.data.objects.ChatsListItem
import com.google.gson.Gson
import com.example.chattest.utils.shared.PrefHelper.set

class SharedManager(private val preferences: SharedPreferences, private val gson: Gson) {

    companion object {
        private const val TOKEN = "TOKEN"
        private const val USER_ID = "CHAT_ID"
        private const val LAST_CHATS = "LAST_CHATS"
    }

    var token: String?
        get() = preferences.getString(TOKEN, "")
        set(value) { preferences[TOKEN] = value }

    var userId: Int?
        get() = preferences.getInt(USER_ID, -1)
        set(value) { preferences[USER_ID] = value }

    var chatList: ChatsListItem
        get() {
            val json = preferences.getString(LAST_CHATS, "")
            return if (json == "") ChatsListItem(arrayListOf())
            else gson.fromJson(json, ChatsListItem::class.java)
        }
        set(value) {
            preferences[LAST_CHATS] = gson.toJson(value)
        }
}