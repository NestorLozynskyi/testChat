package com.example.chattest.ui.screens.authorization

import androidx.lifecycle.MutableLiveData
import com.example.chattest.base.BaseViewModel
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.*
import com.example.chattest.network.Connect
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson

class AuthorizationViewModel(
    private val gson: Gson,
    private val sharedManager: SharedManager,
    private val wsConnect: Connect
) : BaseViewModel() {

    val ld = MutableLiveData<Boolean>()

    fun connect(){
        wsConnect.createConnect()
        wsConnect.swListener({ event, data ->
            when(event){
                Constants.Response.AuthLogin -> {
                    val it = gson.fromJson(data, AuthResponse::class.java)
                    sharedManager.token = it.data.data.token
                    sharedManager.userId = it.data.data.user.id
                    ld.postValue(true)
                }
                Constants.Response.AuthToken -> {
                    val it = gson.fromJson(data, TokenAuthResponse::class.java)
                    if (it.data.data.auth) {
                        sharedManager.userId = it.data.data.user.id
                        ld.postValue(true)
                    }
                }
                else -> println("AuthorizationViewModel: $data")
            }
        },{
            showError.postValue(it)
        })
    }

    fun login(login: String, pass: String){
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.AuthLogin,
                    AuthRequestData(login, pass)
                )
            )
        )
    }
    fun loginViaToken(){
        if (!sharedManager.token.isNullOrBlank()){
            wsConnect.ws?.sendText(
                gson.toJson(
                    Request(
                        Constants.Request.AuthToken,
                        TokenAuthRequest(sharedManager.token!!)
                    )
                )
            )
        }
    }
}