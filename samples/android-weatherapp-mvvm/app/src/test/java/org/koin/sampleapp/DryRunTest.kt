package org.koin.sampleapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.di.remoteDatasourceModule
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.di.weatherApp
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.KoinTest
import org.koin.test.dryRun

class DryRunTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val defaultParameters = mapOf("id" to "ID")

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testRemoteConfiguration() {
        // Use remote web service with SERVER_URL property from koin.properties file
        startKoin(weatherApp + remoteDatasourceModule)
        dryRun { defaultParameters }
    }

    @Test
    fun testLocalConfiguration() {
        startKoin(testApp)
        dryRun { defaultParameters }
    }
}