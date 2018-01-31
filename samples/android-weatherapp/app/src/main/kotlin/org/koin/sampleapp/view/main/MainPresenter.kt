package org.koin.sampleapp.view.main

import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.rx.with
import org.koin.sampleapp.view.AbstractPresenter

class MainPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider) : AbstractPresenter<MainContract.View, MainContract.Presenter>(), MainContract.Presenter {

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