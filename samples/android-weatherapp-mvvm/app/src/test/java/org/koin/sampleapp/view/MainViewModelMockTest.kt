//package org.koin.sampleapp.view
//
//import android.arch.core.executor.testing.InstantTaskExecutorRule
//import android.arch.lifecycle.Observer
//import io.reactivex.Completable
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.koin.sampleapp.repository.WeatherRepository
//import org.koin.sampleapp.util.TestSchedulerProvider
//import org.koin.sampleapp.view.main.MainUIModel
//import org.koin.sampleapp.view.main.MainViewModel
//import org.mockito.ArgumentMatchers
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.Mockito.`when`
//import org.mockito.MockitoAnnotations
//
//
//class MainViewModelMockTest {
//
//    val locationString = "Paris, france"
//
//    lateinit var mainViewModel: MainViewModel
//
//    @Mock lateinit var repository: WeatherRepository
//    @Mock lateinit var observer: Observer<MainUIModel>
//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//
//    @Before
//    fun before() {
//        MockitoAnnotations.initMocks(this)
//        mainViewModel = MainViewModel(repository, TestSchedulerProvider())
//    }
//
//    @Test
//    fun testGetWeather() {
//        `when`(repository.searchWeather(ArgumentMatchers.anyString())).thenReturn(Completable.complete())
//
//        mainViewModel.weatherSearch.observeForever(observer)
//
//        mainViewModel.searchWeather(locationString)
//
//        Mockito.verify(observer).onChanged(MainUIModel(locationString, true))
//        Mockito.verify(observer).onChanged(MainUIModel(locationString, false))
//    }
//
//    @Test
//    fun testGetWeatherFailed() {
//        val error = IllegalStateException("error !")
//        `when`(repository.searchWeather(ArgumentMatchers.anyString())).thenReturn(Completable.error(error))
//
//        mainViewModel.weatherSearch.observeForever(observer)
//
//        mainViewModel.searchWeather(locationString)
//
//        Mockito.verify(observer).onChanged(MainUIModel(locationString, true))
//        Mockito.verify(observer).onChanged(MainUIModel(locationString, error = error))
//    }
//}