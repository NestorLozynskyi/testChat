package com.example.chattest.utils.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CoroutineHelper(private val scope: CoroutineScope) {

    fun launch(coroutineContext: CoroutineContext = Dispatchers.IO,
               block: suspend CoroutineScope.() -> Unit,
               onError: (e: Throwable) -> Unit
    )  = scope.launch(coroutineContext + ExceptionHandler(onError)) {

        block()
    }

    inner class ExceptionHandler(private val onError : (Throwable) -> Unit) :
        CoroutineExceptionHandler {
        override val key: CoroutineContext.Key<*>
            get() = CoroutineExceptionHandler.Key

        override fun handleException(context: CoroutineContext, exception: Throwable) {
            scope.launch(Dispatchers.Main) { onError(exception) }
        }
    }
}