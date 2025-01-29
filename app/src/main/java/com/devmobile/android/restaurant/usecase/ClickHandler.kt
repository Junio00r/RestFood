package com.devmobile.android.restaurant.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClickHandler(private val coroutineScope: CoroutineScope) {
    private val dispatcher = Dispatchers.IO
    private val jobs: ArrayList<Job> = arrayListOf()

    fun run(block: suspend () -> (Unit)) {
        jobs.add(
            coroutineScope.launch(dispatcher) {
                block()
            }
        )
    }

    fun hasJobs(): Boolean {
        return jobs.isNotEmpty()
    }

    fun cancelAll() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }
}