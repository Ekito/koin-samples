package org.koin.sampleapp.view.weather

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.coroutines.SchedulerProvider

/**
 * Weather Presenter
 */
class WeatherResultViewModel(private val weatherRepository: WeatherRepository, private val schedulerProvider: SchedulerProvider) : ViewModel() {

    val currentSearch = MutableLiveData<WeatherResultUIModel>()
    var jobs = listOf<Job>()

    init {
        getWeatherList()
    }

    fun getWeatherList() = launch(schedulerProvider.ui()) {
        try {
            weatherRepository.getWeather().let {
                jobs += it
                currentSearch.value = WeatherResultUIModel(it.await())
            }
        } catch (e: Exception) {
            currentSearch.value = WeatherResultUIModel(error = e)
        }
    }

    fun selectWeatherDetail(detail: DailyForecastModel) = launch(schedulerProvider.ui()) {
        weatherRepository.selectWeatherDetail(detail).let {
            jobs += it
            it.await()
            currentSearch.value = WeatherResultUIModel(selected = true)
            val weather = weatherRepository.getWeather()
            jobs += weather
            currentSearch.value = WeatherResultUIModel(weather.await(), selected = false)
        }
    }

    override fun onCleared() {
        jobs.forEach { it.cancel() }
    }
}


data class WeatherResultUIModel(val list: List<DailyForecastModel> = emptyList(), val selected: Boolean = false, val error: Throwable? = null)