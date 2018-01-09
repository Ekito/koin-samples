package org.koin.sampleapp.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.di.testLocalDatasource
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

class WeatherResultViewModelTest : KoinTest {

    val viewModel: WeatherResultViewModel by inject()
    val repository: WeatherRepository by inject()


    @Mock lateinit var observer: Observer<WeatherResultUIModel>
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin(testLocalDatasource)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testGotList() = runBlocking {
        repository.searchWeather("test").join()

        viewModel.currentSearch.observeForever(observer)
        viewModel.jobs.forEach { it.join() }

        val value = viewModel.currentSearch.value ?: error("No value for view model")

        Mockito.verify(observer).onChanged(WeatherResultUIModel(value.list))
    }

    @Test
    fun testSelected() = runBlocking {
        viewModel.currentSearch.observeForever(observer)

        repository.searchWeather("test").join()

        viewModel.getWeatherList()
        viewModel.jobs.forEach { it.join() }

        val value = viewModel.currentSearch.value ?: error("No value for view model")
        Mockito.verify(observer).onChanged(WeatherResultUIModel(value.list))

        val detail = value.list.first()
        viewModel.selectWeatherDetail(detail)
        viewModel.jobs.forEach { it.join() }
        Mockito.verify(observer).onChanged(WeatherResultUIModel(selected = true))
    }

}