package org.koin.sampleapp.view.detail

import android.arch.lifecycle.MutableLiveData
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.coroutines.SchedulerProvider
import org.koin.sampleapp.view.AbstractViewModel

/**
 * Weather Presenter
 */
class WeatherDetailViewModel(private val weatherRepository: WeatherRepository, schedulerProvider: SchedulerProvider) : AbstractViewModel(schedulerProvider) {

    val detail = MutableLiveData<DailyForecastModel>()

    init {
        getDetail()
    }

    private fun getDetail() {
        launch {
            detail.value = weatherRepository.getSelectedWeatherDetail().await()
        }
    }
}