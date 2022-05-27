package com.example.chattest.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    var showError: MutableLiveData<String> = MutableLiveData()

    //private var coroutineHelper = CoroutineHelper(viewModelScope)

    /*protected open fun launch(
        onError: (e: Throwable) -> Unit,
        coroutineContext: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return coroutineHelper.launch(coroutineContext, block, onError)
    }*/

    open fun onErrorHandler(throwable: Throwable) {
    }
}