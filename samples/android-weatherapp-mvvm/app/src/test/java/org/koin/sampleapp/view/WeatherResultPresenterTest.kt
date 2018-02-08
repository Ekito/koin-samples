package org.koin.sampleapp.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.view.weather.SelectEvent
import org.koin.sampleapp.view.weather.WeatherResultUIModel
import org.koin.sampleapp.view.weather.WeatherResultViewModel
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherResultPresenterTest : KoinTest {

    val viewModel: WeatherResultViewModel by inject()
    val repository: WeatherRepository by inject()

    @Mock
    lateinit var listObserver: Observer<WeatherResultUIModel>
    @Mock
    lateinit var selectObserver: Observer<SelectEvent>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin(testApp)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testGotList() {
        repository.searchWeather("test").blockingGet()

        viewModel.weatherList.observeForever(listObserver)
        viewModel.getWeatherList()

        val value = viewModel.weatherList.value ?: error("No value for view model")

        Mockito.verify(listObserver).onChanged(WeatherResultUIModel(value.list))
    }

    @Test
    fun testSelected() {
        viewModel.weatherList.observeForever(listObserver)
        viewModel.selectEvent.observeForever(selectObserver)

        repository.searchWeather("test").blockingGet()

        viewModel.getWeatherList()

        val value = viewModel.weatherList.value ?: error("No value for view model")
        Mockito.verify(listObserver).onChanged(WeatherResultUIModel(value.list))

        val detail = value.list.first()
        val id = detail.id
        viewModel.selectWeatherDetail(id)

        Mockito.verify(selectObserver).onChanged(SelectEvent(id))
    }
}