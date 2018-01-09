package org.koin.sampleapp.view.weather

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository

/**
 * Weather Presenter
 */
class WeatherResultViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    val currentSearch = MutableLiveData<WeatherResultUIModel>()
    var jobs = listOf<Job>()

    init {
        getWeatherList()
    }

    fun getWeatherList() = async {
        try {
            weatherRepository.getWeather().let {
                jobs += it
                currentSearch.value = WeatherResultUIModel(it.await())
            }
        } catch (e: Exception) {
            currentSearch.value = WeatherResultUIModel(error = e)
        }
    }

    fun selectWeatherDetail(detail: DailyForecastModel) = async {
        weatherRepository.selectWeatherDetail(detail).let {
            jobs += it
            it.await()
            currentSearch.value = WeatherResultUIModel(selected = true)
            currentSearch.value = WeatherResultUIModel(selected = false)
        }
    }

    override fun onCleared() {
        jobs.forEach { it.cancel() }
    }
}


data class WeatherResultUIModel(val list: List<DailyForecastModel> = emptyList(), val selected: Boolean = false, val error: Throwable? = null)