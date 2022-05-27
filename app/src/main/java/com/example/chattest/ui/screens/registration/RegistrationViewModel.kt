package com.example.chattest.ui.screens.registration

import com.example.chattest.base.BaseViewModel
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.RegistrationRequestData
import com.example.chattest.data.objects.Request
import com.example.chattest.network.Connect
import com.google.gson.Gson

class RegistrationViewModel(
    private val gson: Gson,
    private val wsConnect: Connect
) : BaseViewModel() {

    fun registration(login: String, email: String, pass: String, repass: String){
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.RegNewAccount,
                    RegistrationRequestData(
                        login, email, pass, repass
                    )
                )
            )
        )
    }
}