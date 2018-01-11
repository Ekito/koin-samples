package org.koin.sampleapp.view.main

import android.arch.lifecycle.MutableLiveData
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.coroutines.SchedulerProvider
import org.koin.sampleapp.view.AbstractViewModel
import org.koin.sampleapp.view.SingleLiveEvent

class MainViewModel(private val weatherRepository: WeatherRepository, schedulerProvider: SchedulerProvider) : AbstractViewModel(schedulerProvider) {

    val searchEvent = SingleLiveEvent<SearchEvent>()
    val uiData = MutableLiveData<MainUIModel>()

    fun searchWeather(address: String) {
        launch {
            uiData.value = MainUIModel(address)
            try {
                // make loading
                searchEvent.postValue(SearchEvent(isLoading = true))
                weatherRepository.searchWeather(address).await()
                // load ok
                searchEvent.postValue(SearchEvent(isSuccess = true))
            } catch (e: Exception) {
                searchEvent.postValue(SearchEvent(error = e))
            }
        }
    }
}

data class MainUIModel(val searchText: String? = null)
data class SearchEvent(val isLoading: Boolean = false, val isSuccess: Boolean = false, val error: Throwable? = null)