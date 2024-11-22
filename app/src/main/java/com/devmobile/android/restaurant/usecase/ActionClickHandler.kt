package com.devmobile.android.restaurant.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ActionClickHandler(private val coroutineScope: CoroutineScope) {
    private val dispatcher = Dispatchers.IO

    fun run(execute: suspend () -> Unit): Job {
        return coroutineScope.launch(dispatcher) {
            execute()
        }
    }

    fun cancel() {
        coroutineScope.cancel()
    }
}