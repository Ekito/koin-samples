package org.koin.sampleapp.repository.local

import org.koin.sampleapp.repository.data.geocode.Geocode
import org.koin.sampleapp.repository.data.geocode.Location
import org.koin.sampleapp.repository.data.weather.Weather

/**
 * Json reader
 */
interface JsonReader {
    fun getAllLocations(): Map<Location, String>
    fun getWeather(name: String): Weather
    fun getGeocode(name: String): Geocode
}