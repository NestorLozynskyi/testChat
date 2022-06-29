package com.example.chattest.di.koin

import com.example.chattest.network.Connect
import com.example.chattest.ui.screens.authorization.AuthorizationViewModel
import com.example.chattest.ui.screens.chat.ChatViewModel
import com.example.chattest.ui.screens.chooseChat.ChooseChatViewModel
import com.example.chattest.ui.screens.registration.RegistrationViewModel
import com.example.chattest.ui.screens.splash.SplashViewModel
import com.example.chattest.utils.shared.PrefHelper
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val retrofitModule = module(override = true) {
    single<Gson> { GsonBuilder().setLenient().create() }
    single { Connect(get()) }
}

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { AuthorizationViewModel(get(), get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { ChooseChatViewModel(get(), get()) }
    viewModel { ChatViewModel(get(), get()) }
}

val sharedPrefModule = module {

    factory { PrefHelper.customPrefs(get(), "ChatTest_") }

    factory { SharedManager(get(), get()) }
}