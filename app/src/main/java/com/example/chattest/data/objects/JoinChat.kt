package com.example.chattest.data.objects

data class JoinChatRequest(
    val roomId: String
)

data class JoinChatResponse(
    val event: String,
    val data: JoinChatResponseBody
)

data class JoinChatResponseBody(
    val meta: ResponseMeta,
    val data: Chat
)
