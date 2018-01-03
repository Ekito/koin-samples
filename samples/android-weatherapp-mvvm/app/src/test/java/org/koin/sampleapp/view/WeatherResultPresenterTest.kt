package org.koin.sampleapp.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.di.testRemoteDatasource
import org.koin.sampleapp.repository.WeatherRepository
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


    @Mock lateinit var observer: Observer<WeatherResultUIModel>
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin(testRemoteDatasource)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testGotList() {
        repository.searchWeather("test").blockingGet()

        viewModel.currentSearch.observeForever(observer)

        val value = viewModel.currentSearch.value ?: error("No value for view model")

        Mockito.verify(observer).onChanged(WeatherResultUIModel(value.list))
    }

    @Test
    fun testSelected() {
        repository.searchWeather("test").blockingGet()

        viewModel.currentSearch.observeForever(observer)
        val value = viewModel.currentSearch.value ?: error("No value for view model")
        Mockito.verify(observer).onChanged(WeatherResultUIModel(value.list))

        val detail = value.list.first()
        viewModel.selectWeatherDetail(detail)

        Mockito.verify(observer).onChanged(WeatherResultUIModel(selected = true))
    }

}