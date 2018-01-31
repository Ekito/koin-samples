package org.koin.sampleapp.util.rx

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single

/**
 * Rx Scheduler Provider
 */
interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}

/**
 * Use SchedulerProvider configuration for Observable
 */
fun Completable.with(schedulerProvider: SchedulerProvider): Completable =
        this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T> =
        this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())