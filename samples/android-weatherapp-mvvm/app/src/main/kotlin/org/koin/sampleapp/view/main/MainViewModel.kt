package org.koin.sampleapp.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.rx.with

class MainViewModel(private val weatherRepository: WeatherRepository, private val scheduler: SchedulerProvider) : ViewModel() {

    private var disposable: Disposable? = null
    val weatherSearch = MutableLiveData<MainUIModel>()

    fun searchWeather(address: String) {
        weatherSearch.value = MainUIModel(address, true)
        disposable = weatherRepository.searchWeather(address)
                .with(scheduler)
                .subscribe(
                        {
                            weatherSearch.value = MainUIModel(address, false, true)
                            weatherSearch.value = MainUIModel(address)
                        },
                        { err ->
                            weatherSearch.value = MainUIModel(address, error = err)
                        })
    }

    override fun onCleared() {
        disposable?.dispose()
        disposable = null
    }
}

data class MainUIModel(val searchText: String = "", val isLoading: Boolean = false, val isSuccess: Boolean = false, val error: Throwable? = null)