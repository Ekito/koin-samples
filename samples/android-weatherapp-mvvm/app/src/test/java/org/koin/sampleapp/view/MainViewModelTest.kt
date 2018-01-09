package org.koin.sampleapp.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.di.testLocalDatasource
import org.koin.sampleapp.view.main.MainUIModel
import org.koin.sampleapp.view.main.MainViewModel
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainViewModelTest : KoinTest {

    val locationString = "Paris, france"

    val mainViewModel: MainViewModel by inject()

    @Mock lateinit var observer: Observer<MainUIModel>

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
    fun testGetWeather() = runBlocking {
        mainViewModel.weatherSearch.observeForever(observer)

        mainViewModel.searchWeather(locationString)
        mainViewModel.jobs.forEach { it.join() }

        verify(observer).onChanged(MainUIModel(locationString, true))
        verify(observer).onChanged(MainUIModel(locationString, false))
    }
}