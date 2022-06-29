package com.example.chattest.ui.screens.splash

import androidx.lifecycle.MutableLiveData
import com.example.chattest.base.BaseViewModel
import com.example.chattest.network.Connect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashViewModel(
    private val ws: Connect
): BaseViewModel() {

    //override var wsConnect: Connect? = null

    val ld = MutableLiveData<Boolean>()

    fun init(){
        GlobalScope.launch (Dispatchers.IO) {
            ld.postValue(ws.createNewConnect())
        }
    }
}