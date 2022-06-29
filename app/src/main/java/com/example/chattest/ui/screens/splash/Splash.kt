package com.example.chattest.ui.screens.splash

import com.example.chattest.R
import com.example.chattest.base.BaseFragment
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.Request
import com.example.chattest.data.objects.TokenAuthRequest
import com.example.chattest.data.objects.TokenAuthResponse
import com.example.chattest.databinding.FragmentSplashBinding
import com.example.chattest.network.Connect
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*

class Splash : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::class.java) {

    private val wsConnect: Connect by inject()
    private val sharedManager: SharedManager by inject()
    private val gson: Gson by inject()

    private val listener = false

    override fun created() {
        connect()
    }

    private fun connect(){
        GlobalScope.launch (Dispatchers.IO) {
            if (wsConnect.createConnect()) {
                activity?.runOnUiThread {
                    navController?.navigate(R.id.action_splashFragment_to_authorizationFragment)
                }
            } else {
                activity?.runOnUiThread {
                    if (!listener){
                        binding.tv.text = "Try again"
                        tryAgain()
                    } else{
                        binding.tv.text = "Try again"
                    }
                }
            }
        }
    }

    private fun tryAgain(){
        binding.tv.setOnClickListener {
            connect()
        }
    }

}