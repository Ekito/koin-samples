package org.koin.sampleapp.view.weather

import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.repository.json.getDailyForecasts
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.rx.with
import org.koin.sampleapp.view.AbstractPresenter

/**
 * Weather Presenter
 */
class WeatherResultPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider) : AbstractPresenter<WeatherResultContract.View, WeatherResultContract.Presenter>(), WeatherResultContract.Presenter {

    override fun getWeather() {
        launch {
            weatherRepository.getWeather()
                    .with(schedulerProvider)
                    .map { it.getDailyForecasts() }
                    .subscribe(
                            { weatherList -> view.displayWeather(weatherList) },
                            { error -> view.displayError(error) })
        }
    }

    override fun selectWeatherDetail(detail: DailyForecastModel) {
        launch {
            weatherRepository.selectWeatherDetail(detail)
                    .with(schedulerProvider)
                    .subscribe({
                        view.onDetailSaved()
                    }, { err ->
                        view.displayError(err)
                    })
        }
    }
}