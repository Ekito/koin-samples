package org.koin.sampleapp.repository.local

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.koin.sampleapp.repository.WeatherDatasource
import org.koin.sampleapp.repository.json.geocode.Geocode
import org.koin.sampleapp.repository.json.geocode.Location
import org.koin.sampleapp.repository.json.weather.Weather

class LocalDataSource(private val jsonReader: JsonReader) : WeatherDatasource {
    private val cities = HashMap<Location, String>()
    private val defaultCity = "toulouse"

    init {
        cities += jsonReader.getAllLocations()
    }

    private fun isKnownCity(adrs: String): Boolean = cities.values.contains(adrs)

    private fun cityFromLocation(lat: Double?, lng: Double?): Deferred<String> = async {
        var city = "toulouse"
        cities.keys
                .filter { it.lat == lat && it.lng == lng }
                .forEach { city = cities[it]!! }

        city
    }

    override fun geocode(address: String): Deferred<Geocode> = async {
        val adrs = address.toLowerCase()
        if (isKnownCity(adrs)) {
            jsonReader.getGeocode(adrs)
        } else {
            jsonReader.getGeocode(defaultCity)
        }
    }

    override fun weather(lat: Double?, lon: Double?, lang: String): Deferred<Weather> = async {
        val city = cityFromLocation(lat, lon).await()
        jsonReader.getWeather(city)
    }
}