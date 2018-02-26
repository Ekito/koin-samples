package org.koin.sampleapp.view

import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.TestSchedulerProvider
import org.koin.sampleapp.util.any
import org.koin.sampleapp.view.search.SearchContract
import org.koin.sampleapp.view.search.SearchPresenter
import org.koin.test.KoinTest
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SearchPresenterFailureTest : KoinTest {

    lateinit var presenter: SearchContract.Presenter
    @Mock
    lateinit var view: SearchContract.View
    @Mock
    lateinit var repository: WeatherRepository

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        presenter = SearchPresenter(repository, TestSchedulerProvider())
        presenter.view = view
    }

    @Test
    fun testGetWeather() {
        val locationString = "Paris, france"

        `when`(repository.searchWeather(ArgumentMatchers.anyString())).thenReturn(Completable.error(IllegalStateException("Go an error")))

        presenter.getWeather(locationString)

        Mockito.verify(view).displayNormal()
        Mockito.verify(view).onWeatherFailed(any())
    }
}