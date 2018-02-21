package org.koin.sampleapp.view.search

import android.arch.lifecycle.MutableLiveData
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.ext.with
import org.koin.sampleapp.view.AbstractViewModel
import org.koin.sampleapp.view.SingleLiveEvent

class SearchViewModel(private val weatherRepository: WeatherRepository, private val scheduler: SchedulerProvider) : AbstractViewModel() {

    val searchEvent = SingleLiveEvent<SearchEvent>()
    val uiData = MutableLiveData<SearchUIModel>()

    fun searchWeather(address: String) {
        launch {
            uiData.value = SearchUIModel(address)
            searchEvent.value = SearchEvent(isLoading = true)

            weatherRepository.searchWeather(address)
                    .with(scheduler)
                    .subscribe(
                            {
                                searchEvent.postValue(SearchEvent(isSuccess = true))
                            },
                            { err ->
                                searchEvent.postValue(SearchEvent(error = err))
                            })
        }
    }
}

data class SearchUIModel(val searchText: String? = null)
data class SearchEvent(val isLoading: Boolean = false, val isSuccess: Boolean = false, val error: Throwable? = null)