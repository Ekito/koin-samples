package org.koin.sampleapp.view.weather

import android.arch.lifecycle.MutableLiveData
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.coroutines.SchedulerProvider
import org.koin.sampleapp.view.AbstractViewModel

/**
 * Weather Presenter
 */
class WeatherResultViewModel(private val weatherRepository: WeatherRepository, schedulerProvider: SchedulerProvider) : AbstractViewModel(schedulerProvider) {

    val currentSearch = MutableLiveData<WeatherResultUIModel>()

    init {
        getWeatherList()
    }

    fun getWeatherList() {
        launch {
            try {
                val list = weatherRepository.getWeather()
                currentSearch.value = WeatherResultUIModel(list.await())
            } catch (e: Exception) {
                currentSearch.value = WeatherResultUIModel(error = e)
            }
        }
    }

    fun selectWeatherDetail(detail: DailyForecastModel) {
        launch {
            weatherRepository.selectWeatherDetail(detail).await()
            currentSearch.value = WeatherResultUIModel(selected = true)
            val weather = weatherRepository.getWeather()
            currentSearch.value = WeatherResultUIModel(weather.await(), selected = false)
        }
    }
}


data class WeatherResultUIModel(val list: List<DailyForecastModel> = emptyList(), val selected: Boolean = false, val error: Throwable? = null)