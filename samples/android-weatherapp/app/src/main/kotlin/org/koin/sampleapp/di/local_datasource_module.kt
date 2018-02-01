package org.koin.sampleapp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.repository.WeatherDatasource
import org.koin.sampleapp.repository.local.AndroidJsonReader
import org.koin.sampleapp.repository.local.JsonReader
import org.koin.sampleapp.repository.local.LocalDataSource


val localAndroidDatasourceModule = applicationContext {
    bean { AndroidJsonReader(androidApplication()) as JsonReader }
    bean { LocalDataSource(get()) as WeatherDatasource }
}