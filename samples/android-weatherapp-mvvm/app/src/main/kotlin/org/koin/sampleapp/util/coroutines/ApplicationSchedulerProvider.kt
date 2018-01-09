package org.koin.sampleapp.util.coroutines

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI

/**
 * Application providers
 */
class ApplicationSchedulerProvider : SchedulerProvider {
    override fun background() = CommonPool
    override fun ui() = UI
}