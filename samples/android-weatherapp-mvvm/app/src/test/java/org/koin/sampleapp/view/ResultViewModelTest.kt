package org.koin.sampleapp.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.view.result.ResultSelectEvent
import org.koin.sampleapp.view.result.ResultUIModel
import org.koin.sampleapp.view.result.ResultViewModel
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ResultViewModelTest : KoinTest {

    val viewModel: ResultViewModel by inject()
    val repository: WeatherRepository by inject()

    @Mock
    lateinit var listObserver: Observer<ResultUIModel>
    @Mock
    lateinit var selectObserver: Observer<ResultSelectEvent>

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

        viewModel.uiData.observeForever(listObserver)
        viewModel.getWeatherList()

        val value = viewModel.uiData.value ?: error("No value for view myModel")

        Mockito.verify(listObserver).onChanged(ResultUIModel(value.list))
    }

    @Test
    fun testSelected() {
        viewModel.uiData.observeForever(listObserver)
        viewModel.selectEvent.observeForever(selectObserver)

        repository.searchWeather("test").blockingGet()

        viewModel.getWeatherList()

        val value = viewModel.uiData.value ?: error("No value for view myModel")
        Mockito.verify(listObserver).onChanged(ResultUIModel(value.list))

        val detail = value.list.first()
        val id = detail.id
        viewModel.selectWeatherDetail(id)

        Mockito.verify(selectObserver).onChanged(ResultSelectEvent(id))
    }
}