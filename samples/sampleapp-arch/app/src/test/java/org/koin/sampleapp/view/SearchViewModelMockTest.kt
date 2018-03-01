package org.koin.sampleapp.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.TestSchedulerProvider
import org.koin.sampleapp.view.search.SearchUIModel
import org.koin.sampleapp.view.search.SearchViewModel
import org.koin.sampleapp.view.search.SearchEvent
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class SearchViewModelMockTest {

    val locationString = "Paris, france"

    lateinit var searchViewModel: SearchViewModel

    @Mock lateinit var repository: WeatherRepository
    @Mock lateinit var searchObserver: Observer<SearchEvent>
    @Mock lateinit var uiObserver: Observer<SearchUIModel>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        searchViewModel = SearchViewModel(repository, TestSchedulerProvider())
    }

    @Test
    fun testGetWeather() {
        `when`(repository.searchWeather(ArgumentMatchers.anyString())).thenReturn(Completable.complete())

        searchViewModel.searchEvent.observeForever(searchObserver)
        searchViewModel.uiData.observeForever(uiObserver)


        searchViewModel.searchWeather(locationString)

        Mockito.verify(searchObserver).onChanged(SearchEvent(isLoading = true))
        Mockito.verify(searchObserver).onChanged(SearchEvent(isSuccess = true))
        Mockito.verify(uiObserver).onChanged(SearchUIModel(locationString))
    }

    @Test
    fun testGetWeatherFailed() {
        val error = IllegalStateException("Got an error !")
        `when`(repository.searchWeather(ArgumentMatchers.anyString())).thenReturn(Completable.error(error))

        searchViewModel.searchEvent.observeForever(searchObserver)
        searchViewModel.uiData.observeForever(uiObserver)

        searchViewModel.searchWeather(locationString)

        Mockito.verify(searchObserver).onChanged(SearchEvent(isLoading = true))
        Mockito.verify(searchObserver).onChanged(SearchEvent(error = error))
        Mockito.verify(uiObserver).onChanged(SearchUIModel(locationString))
    }
}