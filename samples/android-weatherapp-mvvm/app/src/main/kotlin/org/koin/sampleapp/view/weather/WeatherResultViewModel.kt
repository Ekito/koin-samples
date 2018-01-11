package org.koin.sampleapp.view.weather

import android.arch.lifecycle.MutableLiveData
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.coroutines.SchedulerProvider
import org.koin.sampleapp.view.AbstractViewModel
import org.koin.sampleapp.view.SingleLiveEvent

/**
 * Weather Presenter
 */
class WeatherResultViewModel(private val weatherRepository: WeatherRepository, schedulerProvider: SchedulerProvider) : AbstractViewModel(schedulerProvider) {

    val weatherList = MutableLiveData<WeatherResultUIModel>()
    val selectEvent = SingleLiveEvent<SelectEvent>()

    init {
        getWeatherList()
    }

    fun getWeatherList() {
        launch {
            try {
                // weather list
                weatherList.value = WeatherResultUIModel(weatherRepository.getWeather().await())
            } catch (e: Exception) {
                weatherList.value = WeatherResultUIModel(error = e)
            }
        }
    }

    fun selectWeatherDetail(detail: DailyForecastModel) {
        launch {
            // select detail
            weatherRepository.selectWeatherDetail(detail).await()
            selectEvent.postValue(SelectEvent(isSelected = true))
        }
    }
}


data class WeatherResultUIModel(val list: List<DailyForecastModel> = emptyList(), val error: Throwable? = null)
data class SelectEvent(val isSelected: Boolean = false, val error: Throwable? = null)