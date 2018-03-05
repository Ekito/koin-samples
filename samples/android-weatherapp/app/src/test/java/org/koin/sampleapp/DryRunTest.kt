package org.koin.sampleapp

import org.junit.After
import org.junit.Test
import org.koin.sampleapp.di.Params.DETAIL_VIEW
import org.koin.sampleapp.di.Params.RESULT_VIEW
import org.koin.sampleapp.di.Params.SEARCH_VIEW
import org.koin.sampleapp.di.remoteDatasourceModule
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.di.weatherApp
import org.koin.sampleapp.view.detail.DetailContract
import org.koin.sampleapp.view.result.ResultListContract
import org.koin.sampleapp.view.search.SearchContract
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.KoinTest
import org.koin.test.dryRun
import org.mockito.Mockito.mock

class DryRunTest : KoinTest {

    val params = mapOf(
        DETAIL_VIEW to mock(DetailContract.View::class.java),
        SEARCH_VIEW to mock(SearchContract.View::class.java),
        RESULT_VIEW to mock(ResultListContract.View::class.java)
    )

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testRemoteConfiguration() {
        startKoin(weatherApp + remoteDatasourceModule)
        dryRun { params }
    }

    @Test
    fun testLocalConfiguration() {
        startKoin(testApp)
        dryRun { params }
    }
}