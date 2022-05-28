package com.example.chattest.data.constants

object Constants {
    const val socketChatApi = "ws://74.208.18.242:8081"
    const val TIMEOUT = 60.toLong()

    object Request {
        const val AuthLogin = "auth-login-request"
        const val AuthToken = "auth-token-request"
        const val RegNewAccount = "reg-new-account-request"
        const val RoomCreate = "room-create-request"
        const val RoomJoin = "room-join-request"
        const val GetChatUsers = "get-chat-users-request"
        const val GetChatMessage = "get-chat-messages-request"
        const val SendMessage = "send-message-request"
    }
    object Response{
        const val AuthLogin = "auth-login-response"
        const val AuthToken = "auth-token-response"
        const val RegNewAccount = "reg-new-account-response"
        const val RoomCreate = "room-create-response"
        const val RoomJoin = "room-join-response"
        const val GetChatUsers = "get-chat-users-response"
        const val GetChatMessage = "get-chat-messages-response"
        const val SendMessage = "send-message-response"
        const val SendMessageSelf = "send-message-self-response"
    }
}
