package org.koin.sampleapp.di

import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.datasource.JavaReader
import org.koin.sampleapp.repository.WeatherDatasource
import org.koin.sampleapp.repository.local.LocalDataSource
import org.koin.sampleapp.util.TestSchedulerProvider
import org.koin.sampleapp.util.rx.SchedulerProvider


val testDatasourceModule = applicationContext {
    provide { LocalDataSource(JavaReader()) as WeatherDatasource }
}

val RXTestModule = applicationContext {
    // provided components
    provide { TestSchedulerProvider() as SchedulerProvider }
}

val testRemoteDatasource = weatherAppModules + RXTestModule //listOf(RXTestModule, weatherModule, RemoteDataSourceModule)
val testLocalDatasource = weatherAppModules + RXTestModule + testDatasourceModule //listOf(RXTestModule, weatherModule, testDatasourceModule)