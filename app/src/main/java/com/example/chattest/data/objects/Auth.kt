package com.example.chattest.data.objects

data class AuthRequestData(
    val login: String,
    val pass: String
)



data class AuthResponse(
    val event: String,
    val data: AuthResponseBody
)

data class AuthResponseBody(
    val meta: ResponseMeta,
    val data: AuthResponseData
)

data class AuthResponseData(
    val token: String,
    val user: UserData
)

data class UserData(
    val id: Int,
    val name: String,
    val urlAvatar: String
)
