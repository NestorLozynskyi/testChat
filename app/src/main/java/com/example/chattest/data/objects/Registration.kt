package com.example.chattest.data.objects

data class RegistrationRequestData(
    val login: String,
    val email: String,
    val pass: String,
    val repass: String
)


/*data class RegistrationResponse (
    val event: String,
    val data: RegistrationResponseBody
)

data class RegistrationResponseBody(
    val meta: ResponseMeta,
    val data: RegistrationResponseData
)

data class RegistrationResponseData(
    val auth: Boolean,
    val user: UserData
)*/