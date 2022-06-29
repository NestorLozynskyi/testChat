package com.example.chattest.ui.screens.registration

import androidx.lifecycle.MutableLiveData
import com.example.chattest.base.BaseViewModel
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.RegistrationRequestData
import com.example.chattest.network.Connect

class RegistrationViewModel(
    private val ws: Connect
) : BaseViewModel() {

    //override var wsConnect: Connect? = null

    val ld = MutableLiveData<Boolean>()

    fun registration(login: String, email: String, pass: String, repass: String){
        ws.sendRequest(
            Constants.Request.RegNewAccount,
            RegistrationRequestData(
                login, email, pass, repass
            )
        )
    }
}