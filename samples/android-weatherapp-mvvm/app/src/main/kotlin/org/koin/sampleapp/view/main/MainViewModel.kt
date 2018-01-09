package org.koin.sampleapp.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.coroutines.SchedulerProvider

class MainViewModel(private val weatherRepository: WeatherRepository, private val schedulerProvider: SchedulerProvider) : ViewModel() {

    val weatherSearch = MutableLiveData<MainUIModel>()
    var jobs = listOf<Job>()

    fun searchWeather(address: String) = launch(schedulerProvider.ui()) {
        weatherSearch.value = MainUIModel(address, true)
        try {
            weatherRepository.searchWeather(address).let {
                jobs += it
                it.await()
                weatherSearch.value = MainUIModel(address, false, true)
                weatherSearch.value = MainUIModel(address)
            }
        } catch (e: Exception) {
            weatherSearch.value = MainUIModel(address, error = e)
        }
    }

    override fun onCleared() {
        jobs.forEach { it.cancel() }
    }
}

data class MainUIModel(val searchText: String = "", val isLoading: Boolean = false, val isSuccess: Boolean = false, val error: Throwable? = null)