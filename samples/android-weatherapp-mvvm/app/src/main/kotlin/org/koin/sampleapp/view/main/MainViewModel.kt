package org.koin.sampleapp.view.main

import android.arch.lifecycle.MutableLiveData
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.coroutines.SchedulerProvider
import org.koin.sampleapp.view.AbstractViewModel

class MainViewModel(private val weatherRepository: WeatherRepository, schedulerProvider: SchedulerProvider) : AbstractViewModel(schedulerProvider) {

    val weatherSearch = MutableLiveData<MainUIModel>()

    fun searchWeather(address: String) {
        launch {
            try {
                // make loading
                weatherSearch.value = MainUIModel(address, true)
                weatherRepository.searchWeather(address).await()
                // load ok
                weatherSearch.value = MainUIModel(address, false, true)
                // default state
                weatherSearch.value = MainUIModel(address)
            } catch (e: Exception) {
                weatherSearch.value = MainUIModel(address, error = e)
            }
        }
    }
}

data class MainUIModel(val searchText: String = "", val isLoading: Boolean = false, val isSuccess: Boolean = false, val error: Throwable? = null)