package org.koin.sampleapp.view

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.sampleapp.di.Params.RESULT_ACTIVITY
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.view.result.ResultListContract
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ResultPresenterTest : KoinTest {

    val view: ResultListContract.View = mock(ResultListContract.View::class.java)
    val presenter: ResultListContract.Presenter by inject { mapOf(RESULT_ACTIVITY to view) }

    @Before
    fun before() {
        startKoin(testApp)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testDisplayWeather() {
        presenter.getWeather()

        Mockito.verify(view).displayWeather(emptyList())
    }

}