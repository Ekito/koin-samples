package org.koin.sampleapp.view.search

import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.ext.with
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.view.AbstractPresenter

class SearchPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider) : AbstractPresenter<SearchContract.View, SearchContract.Presenter>(), SearchContract.Presenter {

    override fun getWeather(address: String) {
        view.displayProgress()
        launch {
            weatherRepository.searchWeather(address)
                    .with(schedulerProvider)
                    .subscribe({
                        view.displayNormal()
                        view.onWeatherSuccess()
                    }, { error ->
                        view.displayNormal()
                        view.onWeatherFailed(error)
                    })
        }
    }
}