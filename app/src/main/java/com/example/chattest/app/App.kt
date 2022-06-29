package com.example.chattest.app

import android.app.Application
import com.example.chattest.di.koin.retrofitModule
import com.example.chattest.di.koin.sharedPrefModule
import com.example.chattest.di.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        private lateinit var app: App
        fun get(): App = app
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        initKoin()
    }

    private fun initKoin() {

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    sharedPrefModule,
                    retrofitModule,
                    viewModelModule
                )
            )
        }
    }
}