package org.koin.sampleapp.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.view.search.SearchUIModel
import org.koin.sampleapp.view.search.SearchViewModel
import org.koin.sampleapp.view.search.SearchEvent
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SearchViewModelTest : KoinTest {

    val locationString = "Paris, france"

    val searchViewModel: SearchViewModel by inject()

    @Mock lateinit var searchObserver: Observer<SearchEvent>
    @Mock lateinit var uiObserver: Observer<SearchUIModel>

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
    fun testGetWeather() {
        searchViewModel.searchEvent.observeForever(searchObserver)
        searchViewModel.uiData.observeForever(uiObserver)

        searchViewModel.searchWeather(locationString)

        verify(searchObserver).onChanged(SearchEvent(isLoading = true))
        verify(searchObserver).onChanged(SearchEvent(isSuccess = true))
        verify(uiObserver).onChanged(SearchUIModel(locationString))
    }
}