package org.koin.sampleapp.view.detail

import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.ext.with
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.view.AbstractPresenter

class DetailPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider, override var view : DetailContract.View) : AbstractPresenter<DetailContract.View, DetailContract.Presenter>(), DetailContract.Presenter {

    override fun getDetail(id: String) {
        launch {
            weatherRepository.getSelectedWeatherDetail(id).with(schedulerProvider).subscribe(
                    { detail ->
                        view.displayDetail(detail)
                    }, { err -> println("DetailPresenter error : $err") })
        }
    }
}