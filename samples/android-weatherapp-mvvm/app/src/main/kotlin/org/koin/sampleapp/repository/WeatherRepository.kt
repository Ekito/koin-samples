package org.koin.sampleapp.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.json.getLocation
import org.koin.sampleapp.repository.json.weather.Weather

/**
 * Weather repository
 */
interface WeatherRepository {
    fun searchWeather(location: String): Completable
    fun getWeather(): Single<Weather>
    fun selectWeatherDetail(detail: DailyForecastModel): Completable
    fun getSelectedWeatherDetail(): Single<DailyForecastModel>
}

/**
 * Weather repository
 * Make use of WeatherDatasource & add some cache
 */
class WeatherRepositoryImpl(private val weatherDatasource: WeatherDatasource) : WeatherRepository {

    var weatherCache: Pair<String, Weather>? = null

    private var detail: DailyForecastModel? = null

    private val DEFAULT_LANG = "EN"

    override fun selectWeatherDetail(detail: DailyForecastModel) =
            Completable.create {
                this.detail = detail
                it.onComplete()
            }

    override fun getSelectedWeatherDetail(): Single<DailyForecastModel> = if (detail != null) Single.just(detail) else Single.error(IllegalStateException("Detail is null ! You must select a detail before"))

    override fun searchWeather(location: String): Completable {
        val cache = weatherCache
        return if (cache?.first == location) {
            Completable.complete()
        } else {
            weatherDatasource.geocode(location)
                    .map { it.getLocation() ?: throw IllegalStateException("No Location data") }
                    .flatMap { weatherDatasource.weather(it.lat, it.lng, DEFAULT_LANG) }
                    .doOnSuccess { weatherCache = Pair(location, it) }
                    .toCompletable()
        }
    }

    override fun getWeather(): Single<Weather> {
        val cache = weatherCache
        return if (cache != null) {
            Single.just(cache.second)
        } else {
            Single.error(IllegalStateException("No request cached - please use searchWeather() before"))
        }
    }

}