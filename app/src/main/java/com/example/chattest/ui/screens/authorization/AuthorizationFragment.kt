package com.example.chattest.ui.screens.authorization

import com.example.chattest.R
import com.example.chattest.base.BaseFragment
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.*
import com.example.chattest.databinding.FragmentAuthorizationBinding
import com.example.chattest.network.Connect
import com.example.chattest.utils.extensions.bindDataTo
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>(FragmentAuthorizationBinding::class.java) {

    private val wsConnect: Connect by inject()
    private val sharedManager: SharedManager by inject()
    private val gson: Gson by inject()

    override fun created() {
        loginViaToken()
        connect()
        with(binding) {
            login.setOnClickListener {
                login(editLogin.text.toString(), editPass.text.toString())
            }
            registration.setOnClickListener {
                try {
                    navController?.navigate(R.id.action_authorizationFragment_to_registrationFragment)
                } catch (e: Exception){}
            }
            /*token.setOnClickListener {
                viewModel.loginViaToken()
            }*/
        }
    }

    /*override fun observe() {
        bindDataTo(viewModel.ld){
            if (it) {
                viewModel.ld.postValue(null)
                goToChooseChat()
            }
        }
    }*/
    private fun goToChooseChat(){
        try {
            navController?.navigate(R.id.action_authorizationFragment_to_chooseChatFragment)
        } catch (e: Exception){}
    }


    private fun connect(){
        //wsConnect.createConnect()
        wsConnect.swListener({ event, data ->
            when(event){
                Constants.Response.AuthLogin -> {
                    val it = gson.fromJson(data, AuthResponse::class.java)
                    sharedManager.token = it.data.data.token
                    sharedManager.userId = it.data.data.user.id
                    goToChooseChat()
                }
                Constants.Response.AuthToken -> {
                    val it = gson.fromJson(data, TokenAuthResponse::class.java)
                    if (it.data.data.auth) {
                        sharedManager.userId = it.data.data.user.id
                        goToChooseChat()
                    }
                }
                /*else -> println("AuthorizationViewModel: $data")*/
            }
        },{
            //showError.postValue(it)
        })
    }

    private fun login(login: String, pass: String){
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