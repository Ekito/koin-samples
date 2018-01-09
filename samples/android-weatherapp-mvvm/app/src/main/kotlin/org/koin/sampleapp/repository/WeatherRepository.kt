package org.koin.sampleapp.repository

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.json.getDailyForecasts
import org.koin.sampleapp.repository.json.getLocation

/**
 * Weather repository
 */
interface WeatherRepository {
    fun searchWeather(location: String): Deferred<Unit>
    fun getWeather(): Deferred<List<DailyForecastModel>>
    fun selectWeatherDetail(detail: DailyForecastModel): Deferred<Unit>
    fun getSelectedWeatherDetail(): Deferred<DailyForecastModel>
}

/**
 * Weather repository
 * Make use of WeatherDatasource & add some cache
 */
class WeatherRepositoryImpl(private val weatherDatasource: WeatherDatasource) : WeatherRepository {

    var weatherCache: Pair<String, List<DailyForecastModel>>? = null

    private var detail: DailyForecastModel? = null

    private val DEFAULT_LANG = "EN"

    override fun selectWeatherDetail(detail: DailyForecastModel) = async {
        this@WeatherRepositoryImpl.detail = detail
    }

    override fun getSelectedWeatherDetail(): Deferred<DailyForecastModel> = async {
        detail ?: throw IllegalStateException("Detail is null ! You must select a detail before")
    }

    override fun searchWeather(location: String): Deferred<Unit> = async {
        val geo = weatherDatasource.geocode(location).await().getLocation() ?: error("Geocode is null")
        val weather = weatherDatasource.weather(geo.lat, geo.lng, DEFAULT_LANG).await()
        weatherCache = Pair(location, weather.getDailyForecasts())
    }

    override fun getWeather(): Deferred<List<DailyForecastModel>> = async {
        if (weatherCache != null) {
            weatherCache?.second ?: throw IllegalStateException("Invalid cache - please use searchWeather() before")
        } else {
            throw IllegalStateException("No request cached - please use searchWeather() before")
        }
    }
}