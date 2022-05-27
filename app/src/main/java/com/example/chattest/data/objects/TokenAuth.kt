package com.example.chattest.data.objects

data class TokenAuthRequest(
    val token: String
)


data class TokenAuthResponse (
    val event: String,
    val data: TokenAuthResponseBody
)

data class TokenAuthResponseBody(
    val meta: ResponseMeta,
    val data: TokenAuthResponseData
)

data class TokenAuthResponseData(
    val auth: Boolean,
    val user: UserData
)