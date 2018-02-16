package org.koin.sampleapp.model

import java.util.*

/**
 * Represents our weather forecast for one day
 */
data class DailyForecastModel(val forecastString: String, val icon: String, val temperatureLow: String, val temperatureHigh: String) {

    val id = UUID.randomUUID().toString()

    val temperatureString = "$temperatureLow°C - $temperatureHigh°C"

}
