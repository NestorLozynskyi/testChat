package com.example.chattest.data.objects

data class Chat(
    val roomId: Int
)

data class GetChatRequest(
    val chatId: Int
)

data class SendMessageRequest(
    val chatId: String,
    val message: String
)

data class ChatResponse(
    val event: String,
    val data: ChatResponseBody
)

data class ChatResponseBody(
    val meta: ResponseMeta,
    val data: ChatResponseData
)

data class ChatResponseData(
    val message: Message
)



data class GetChatResponse(
    val event: String,
    val data: GetChatResponseBody
)

data class GetChatResponseBody(
    val meta: ResponseMeta,
    val data: GetChatResponseData
)

data class GetChatResponseData(
    val messages: ArrayList<Message>
)


data class Message(
    val chatId: Int,
    val userId: Int,
    val message: String,
    val timestamp: Long
)

