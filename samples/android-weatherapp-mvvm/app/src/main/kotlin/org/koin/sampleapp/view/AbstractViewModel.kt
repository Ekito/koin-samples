package org.koin.sampleapp.view

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import org.koin.sampleapp.util.coroutines.SchedulerProvider

abstract class AbstractViewModel(private val schedulerProvider: SchedulerProvider) : ViewModel() {
    var jobs = listOf<Job>()

    fun launch(code: suspend CoroutineScope.() -> Unit) {
        jobs += launch(schedulerProvider.ui(), block = code)
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }
}