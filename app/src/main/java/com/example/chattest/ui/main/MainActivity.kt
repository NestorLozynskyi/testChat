package com.example.chattest.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.chattest.R

class MainActivity : AppCompatActivity() {

    //private val gson: Gson by inject()
    //var wsConnect: Connect = Connect(gson)
    //var ws: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        setContentView(R.layout.activity_main)
        try {
            Navigation.findNavController(this, R.id.navHostFragment)
                .navigate(R.id.action_global_authorizationFragment)

        } catch (e: Exception) {
            println(e)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    /*fun reconnect(){
        wsConnect = Connect(gson)

        GlobalScope.launch (Dispatchers.IO) {
            wsConnect.createNewConnect()
        }
    }*/
}