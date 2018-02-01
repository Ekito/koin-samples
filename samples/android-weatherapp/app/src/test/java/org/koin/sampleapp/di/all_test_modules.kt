package org.koin.sampleapp.di


val testRemoteDatasource = listOf(RxTestModule, weatherModule, remoteDatasourceModule)
val testLocalDatasource = listOf(RxTestModule, weatherModule, localJavaDatasource)