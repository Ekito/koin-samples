package org.koin.sampleapp.di

import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.util.coroutines.ApplicationSchedulerProvider
import org.koin.sampleapp.util.coroutines.SchedulerProvider

val schedulerModule = applicationContext {
    // provided components
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}