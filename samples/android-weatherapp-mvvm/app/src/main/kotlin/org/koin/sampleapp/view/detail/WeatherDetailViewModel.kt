package org.koin.sampleapp.view.detail

import android.arch.lifecycle.MutableLiveData
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.rx.with
import org.koin.sampleapp.view.AbstractViewModel

/**
 * Weather Presenter
 */
class WeatherDetailViewModel(private val weatherRepository: WeatherRepository, private val scheduler: SchedulerProvider) : AbstractViewModel() {

    val detail = MutableLiveData<DailyForecastModel>()

    init {
        getDetail()
    }

    private fun getDetail() {
        launch {
            weatherRepository.getSelectedWeatherDetail().with(scheduler)
                    .subscribe(
                            { d -> detail.value = d },
                            { e -> println("got error : $e") })
        }
    }
}