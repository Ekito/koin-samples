package org.koin.sampleapp.view.detail

import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.rx.with
import org.koin.sampleapp.view.AbstractPresenter

class WeatherDetailPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider) : AbstractPresenter<WeatherDetailContract.View, WeatherDetailContract.Presenter>(), WeatherDetailContract.Presenter {

    override fun getDetail(id: String) {
        launch {
            weatherRepository.getSelectedWeatherDetail(id).with(schedulerProvider).subscribe(
                    { detail ->
                        view.displayDetail(detail)
                    }, { err -> println("WeatherDetailPresenter error : $err") })
        }
    }
}