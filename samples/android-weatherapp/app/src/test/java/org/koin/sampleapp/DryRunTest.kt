package org.koin.sampleapp

import org.junit.After
import org.junit.Test
import org.koin.sampleapp.di.remoteDatasourceModule
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.di.weatherApp
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.KoinTest
import org.koin.test.dryRun

class DryRunTest : KoinTest {

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testRemoteConfiguration() {
        startKoin(weatherApp + remoteDatasourceModule)
        dryRun()
    }

    @Test
    fun testLocalConfiguration() {
        startKoin(testApp)
        dryRun()
    }
}