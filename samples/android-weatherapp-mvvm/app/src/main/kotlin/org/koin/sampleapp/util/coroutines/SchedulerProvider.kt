package org.koin.sampleapp.util.coroutines

import kotlinx.coroutines.experimental.CoroutineDispatcher

/**
 * Rx Scheduler Provider
 */
interface SchedulerProvider {
    fun background(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
}