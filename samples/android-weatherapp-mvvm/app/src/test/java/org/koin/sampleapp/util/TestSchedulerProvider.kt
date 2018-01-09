package org.koin.sampleapp.util

import kotlinx.coroutines.experimental.CommonPool
import org.koin.sampleapp.util.coroutines.SchedulerProvider

class TestSchedulerProvider : SchedulerProvider {
    override fun ui() = CommonPool

    override fun background() = CommonPool
}