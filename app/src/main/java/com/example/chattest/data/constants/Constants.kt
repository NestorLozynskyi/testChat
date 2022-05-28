package com.example.chattest.data.constants

object Constants {
    const val socketChatApi = "ws://74.208.18.242:8081"
    const val TIMEOUT = 60.toLong()

    /*const val AUTH_LOGIN_REQUEST = "auth-login-request"
    const val AUTH_LOGIN_RESPONSE = "auth-login-response"
    const val AUTH_TOKEN_REQUEST = "auth-token-request"
    const val AUTH_TOKEN_RESPONSE = "auth-token-response"
    const val REG_NEW_ACCOUNT_REQUEST = "reg-new-account-request"
    const val REG_NEW_ACCOUNT_RESPONSE = "reg-new-account-response"
    const val ROOM_CREATE_REQUEST = "room-create-request"
    const val ROOM_CREATE_RESPONSE = "room-create-response"
    const val ROOM_JOIN_REQUEST = "room-join-request"
    const val ROOM_JOIN_RESPONSE = "room-join-response"
    const val GET_CHAT_USERS_REQUEST = "get-chat-users-request"
    const val GET_CHAT_USERS_RESPONSE = "get-chat-users-response"
    const val GET_CHAT_MESSAGES_REQUEST = "get-chat-messages-request"
    const val GET_CHAT_MESSAGES_RESPONSE = "get-chat-messages-response"
    const val SEND_MESSAGE_REQUEST = "send-message-request"
    const val SEND_MESSAGE_RESPONSE = "send-message-response"*/

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
