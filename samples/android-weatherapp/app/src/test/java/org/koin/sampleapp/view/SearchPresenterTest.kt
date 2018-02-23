package org.koin.sampleapp.view

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.view.search.SearchContract
import org.koin.sampleapp.view.search.SearchPresenter
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPresenterTest : KoinTest {

    val weatherRepository: WeatherRepository by inject()
    val scheduler: SchedulerProvider by inject()
    lateinit var presenter: SearchContract.Presenter

    @Mock
    lateinit var view: SearchContract.View

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin(testApp)

        presenter = SearchPresenter(weatherRepository, scheduler, view)
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