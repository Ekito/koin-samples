package org.koin.sampleapp.view.weather

import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.repository.json.getDailyForecasts
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.rx.with
import org.koin.sampleapp.view.AbstractPresenter

/**
 * Weather Presenter
 */
class WeatherResultPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider) : AbstractPresenter<WeatherResultContract.View, WeatherResultContract.Presenter>(), WeatherResultContract.Presenter {

    override fun getWeather(address: String) {
        launch {
            weatherRepository.getWeather(address)
                    .with(schedulerProvider)
                    .map { it.getDailyForecasts() }
                    .subscribe(
                            { weatherList -> view.displayWeather(weatherList) },
                            { error -> view.displayError(error) })
        }
    }
}