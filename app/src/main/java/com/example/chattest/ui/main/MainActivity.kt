package com.example.chattest.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.chattest.R
import com.neovisionaries.ws.client.WebSocket

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        try {
            Navigation.findNavController(this, R.id.navHostFragment)
                .navigate(R.id.action_global_authorizationFragment)

        } catch (e: Exception) {
            println(e)
        }
    }
}