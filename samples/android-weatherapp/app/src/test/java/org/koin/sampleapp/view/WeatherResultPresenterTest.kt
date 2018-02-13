package org.koin.sampleapp.view

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.view.weather.WeatherListResultContract
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherResultPresenterTest : KoinTest {

    val presenter by inject<WeatherListResultContract.Presenter>()

    @Mock
    lateinit var view: WeatherListResultContract.View

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin(testApp)

        presenter.view = view
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