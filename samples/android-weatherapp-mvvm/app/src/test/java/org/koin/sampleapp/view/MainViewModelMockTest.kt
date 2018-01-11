package org.koin.sampleapp.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.coroutines.TestSchedulerProvider
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.view.main.MainViewModel
import org.koin.sampleapp.view.main.SearchEvent
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class MainViewModelMockTest {

    val locationString = "Paris, france"

    lateinit var mainViewModel: MainViewModel
    val schedulerProvider = TestSchedulerProvider()

    @Mock lateinit var repository: WeatherRepository
    @Mock lateinit var observer: Observer<SearchEvent>
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(repository, schedulerProvider)
    }

    @Test
    fun testGetWeather() = runBlocking {
        `when`(repository.searchWeather(ArgumentMatchers.anyString())).thenReturn(async(schedulerProvider.ui()) {})

        mainViewModel.searchEvent.observeForever(observer)

        mainViewModel.searchWeather(locationString)
        mainViewModel.jobs.forEach { it.join() }

        Mockito.verify(observer).onChanged(SearchEvent(isLoading = true))
        Mockito.verify(observer).onChanged(SearchEvent(isSuccess = true))
    }

    @Test
    fun testGetWeatherFailed() = runBlocking {
        val error = IllegalStateException("error !")
        `when`(repository.searchWeather(ArgumentMatchers.anyString())).thenReturn(async(schedulerProvider.ui()) { throw error })

        mainViewModel.searchEvent.observeForever(observer)

        mainViewModel.searchWeather(locationString)
        mainViewModel.jobs.forEach { it.join() }

        Mockito.verify(observer).onChanged(SearchEvent(isLoading = true))
        Mockito.verify(observer).onChanged(SearchEvent(error = error))
    }
}