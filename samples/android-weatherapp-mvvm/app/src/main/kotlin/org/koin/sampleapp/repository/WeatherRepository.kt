package org.koin.sampleapp.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.data.getDailyForecasts
import org.koin.sampleapp.repository.data.getLocation

/**
 * Weather repository
 */
interface WeatherRepository {
    fun searchWeather(location: String): Completable
    fun getWeather(): Single<List<DailyForecastModel>>
    fun getSelectedWeatherDetail(id: String): Single<DailyForecastModel>
}

/**
 * Weather repository
 * Make use of WeatherDatasource & add some cache
 */
class WeatherRepositoryImpl(private val weatherDatasource: WeatherDatasource) : WeatherRepository {

    val weatherCache = arrayListOf<DailyForecastModel>()

    override fun getSelectedWeatherDetail(id: String) = Single.just(weatherCache.first { it.id == id })

    override fun searchWeather(location: String): Completable = weatherDatasource.geocode(location)
            .map { it.getLocation() ?: throw IllegalStateException("No Location data") }
            .flatMap { weatherDatasource.weather(it.lat, it.lng, DEFAULT_LANG) }
            .map { it.getDailyForecasts() }
            .doOnSuccess { weatherCache.clear(); weatherCache.addAll(it) }
            .toCompletable()

    override fun getWeather(): Single<List<DailyForecastModel>> = Single.just(weatherCache)

    companion object {
        const val DEFAULT_LANG = "EN"
    }

}