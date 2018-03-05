package org.koin.sampleapp.view

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.sampleapp.di.Params.SEARCH_ACTIVITY
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.view.search.SearchContract
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.mockito.Mockito.mock

class SearchPresenterTest : KoinTest {

    val view: SearchContract.View by lazy { mock(SearchContract.View::class.java) }
    val presenter: SearchContract.Presenter by inject { mapOf(SEARCH_ACTIVITY to view) }


    @Before
    fun before() {
        startKoin(testApp)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testGetWeather() {
        val locationString = "Paris, france"
        presenter.getWeather(locationString)

        Mockito.verify(view).displayNormal()
        Mockito.verify(view).onWeatherSuccess()
    }
}