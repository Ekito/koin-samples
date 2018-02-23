package org.koin.sampleapp

import org.junit.After
import org.junit.Test
import org.koin.sampleapp.di.Params.DETAIL_ACTIVITY
import org.koin.sampleapp.di.Params.SEARCH_ACTIVITY
import org.koin.sampleapp.di.remoteDatasourceModule
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.di.weatherApp
import org.koin.sampleapp.view.detail.DetailActivity
import org.koin.sampleapp.view.search.SearchActivity
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.KoinTest
import org.koin.test.dryRun
import org.mockito.Mockito.mock

class DryRunTest : KoinTest {

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testRemoteConfiguration() {
        startKoin(weatherApp + remoteDatasourceModule)
        dryRun(defaultParameters = mapOf(DETAIL_ACTIVITY to mock(DetailActivity::class.java), SEARCH_ACTIVITY to mock(SearchActivity::class.java)))
    }

    @Test
    fun testLocalConfiguration() {
        startKoin(testApp)
        dryRun(defaultParameters = mapOf(DETAIL_ACTIVITY to mock(DetailActivity::class.java), SEARCH_ACTIVITY to mock(SearchActivity::class.java)))
    }
}