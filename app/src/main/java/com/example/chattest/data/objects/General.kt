package com.example.chattest.data.objects

data class Request(
    val event: String,
    val data: Any//AuthRequestData
)

data class Response(
    val event: String,
    val data: ResponseBody
)
data class ResponseBody(
    val meta: ResponseMeta,
    val data: Any
)
data class ResponseMeta(
    val status: Int,
    val message: String?
)