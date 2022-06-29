package com.example.chattest.di.koin

import com.example.chattest.network.Connect
import com.example.chattest.utils.shared.PrefHelper
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val retrofitModule = module(override = true) {
    single<Gson> { GsonBuilder().setLenient().create() }
    single { Connect(get()) }
}

/*val viewModelModule = module {
    viewModel { AuthorizationViewModel(get(), get(), get()) }
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { ChooseChatViewModel(get(), get(), get()) }
    viewModel { ChatViewModel(get(), get(), get()) }
}*/

val repositoryModule = module {
}

val sharedPrefModule = module {

    factory { PrefHelper.customPrefs(get(), "ChatTest_") }

    factory { SharedManager(get(), get()) }
}