package com.example.chattest.ui.screens.authorization

import androidx.lifecycle.MutableLiveData
import com.example.chattest.base.BaseViewModel
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.AuthRequestData
import com.example.chattest.data.objects.AuthResponse
import com.example.chattest.data.objects.TokenAuthRequest
import com.example.chattest.data.objects.TokenAuthResponse
import com.example.chattest.network.Connect
import com.example.chattest.utils.shared.SharedManager

class AuthorizationViewModel(
    private val ws: Connect,
    private val sharedManager: SharedManager
) : BaseViewModel() {

    //override var wsConnect: Connect? = null

    val ld = MutableLiveData<Boolean>()

    fun listenerWS(){
        ws.wsListener({ event, data ->
            when (event) {
                Constants.Response.AuthLogin -> {
                    val it = ws.convertFromJson(data, AuthResponse::class.java)
                    sharedManager.token = it.data.data.token
                    sharedManager.userId = it.data.data.user.id
                    ld.postValue(true)//goToChooseChat()
                }
                Constants.Response.AuthToken -> {
                    val it = ws.convertFromJson(data, TokenAuthResponse::class.java)
                    if (it.data.data.auth) {
                        sharedManager.userId = it.data.data.user.id
                        ld.postValue(true)//goToChooseChat()
                    }
                }
                /*else -> println("AuthorizationViewModel: $data")*/
            }
        }, {
            //showError.postValue(it)
        })
    }

    /*fun init(){
        if (wsConnect != null) {
            with(wsConnect!!) {
                wsListener({ event, data ->
                    when (event) {
                        Constants.Response.AuthLogin -> {
                            val it = convertFromJson(data, AuthResponse::class.java)
                            sharedManager.token = it.data.data.token
                            sharedManager.userId = it.data.data.user.id
                            ld.postValue(true)//goToChooseChat()
                        }
                        Constants.Response.AuthToken -> {
                            val it = convertFromJson(data, TokenAuthResponse::class.java)
                            if (it.data.data.auth) {
                                sharedManager.userId = it.data.data.user.id
                                ld.postValue(true)//goToChooseChat()
                            }
                        }
                        /*else -> println("AuthorizationViewModel: $data")*/
                    }
                }, {
                    //showError.postValue(it)
                })
            }
        }
    }*/

    fun login(login: String, pass: String){
        ws.sendRequest(
            Constants.Request.AuthLogin,
            AuthRequestData(login, pass)
        )
    }

    fun loginViaToken(){
        if (!sharedManager.token.isNullOrBlank()){
            ws.sendRequest(
                Constants.Request.AuthToken,
                TokenAuthRequest(sharedManager.token!!)
            )
        }
    }

}