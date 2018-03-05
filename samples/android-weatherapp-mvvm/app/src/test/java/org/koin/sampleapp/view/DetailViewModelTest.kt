package org.koin.sampleapp.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.view.detail.DetailViewModel
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailViewModelTest : KoinTest {

    val viewModel: DetailViewModel by inject { mapOf("id" to "ID") }
    val repository: WeatherRepository by inject()

    @Mock
    lateinit var uiData: Observer<DailyForecastModel>

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
    fun testGotDetail() {
        // Setup data
        repository.searchWeather("Toulouse").blockingGet()
        val list = repository.getWeather().blockingGet()

        // Observe
        viewModel.uiData.observeForever(uiData)

        // Select data to notify
        val weather = list.first()
        viewModel.getDetail(weather.id)

        // Has received data
        Assert.assertNotNull(viewModel.uiData.value)

        // Has been notified
        Mockito.verify(uiData).onChanged(weather)
    }
}