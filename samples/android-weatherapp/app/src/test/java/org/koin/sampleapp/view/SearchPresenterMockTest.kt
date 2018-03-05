package org.koin.sampleapp.view

import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.TestSchedulerProvider
import org.koin.sampleapp.view.search.SearchContract
import org.koin.sampleapp.view.search.SearchPresenter
import org.koin.test.KoinTest
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SearchPresenterMockTest : KoinTest {

    lateinit var presenter: SearchContract.Presenter
    @Mock
    lateinit var view: SearchContract.View
    @Mock
    lateinit var repository: WeatherRepository

    val locationString = "Paris, france"

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        presenter = SearchPresenter(repository, TestSchedulerProvider(),view)
    }

    @Test
    fun testGetWeather() {
        `when`(repository.searchWeather(ArgumentMatchers.anyString())).thenReturn(Completable.complete())

        presenter.getWeather(locationString)

        Mockito.verify(view).displayNormal()
        Mockito.verify(view).onWeatherSuccess()
    }

    @Test
    fun testGetWeatherFailed() {
        val error = IllegalStateException("Got an error")

        `when`(repository.searchWeather(ArgumentMatchers.anyString())).thenReturn(Completable.error(error))

        presenter.getWeather(locationString)

        Mockito.verify(view).displayNormal()
        Mockito.verify(view).onWeatherFailed(error)
    }
}