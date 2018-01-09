package org.koin.sampleapp.view.detail

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
class WeatherDetailViewModel(private val weatherRepository: WeatherRepository, private val schedulerProvider: SchedulerProvider) : ViewModel() {

    var jobs = listOf<Job>()
    val detail = MutableLiveData<DailyForecastModel>()

    init {
        getDetail()
    }

    private fun getDetail() = launch(schedulerProvider.ui()) {
        weatherRepository.getSelectedWeatherDetail().let {
            jobs += it
            detail.value = it.await()
        }
    }

    override fun onCleared() {
        jobs.forEach { it.cancel() }
    }
}