package org.koin.sampleapp.view.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_weather_detail.*
import org.koin.android.architecture.ext.viewModel
import org.koin.sampleapp.R
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.util.ext.argument
import org.koin.sampleapp.view.Arguments.ARG_ADDRESS
import org.koin.sampleapp.view.Arguments.ARG_WEATHER_DATE
import org.koin.sampleapp.view.Arguments.ARG_WEATHER_ITEM_ID
import java.util.*

/**
 * Weather Detail View
 */
class DetailActivity : AppCompatActivity() {

    // Get all needed data
    private val address by argument<String>(ARG_ADDRESS)
    private val now by argument<Date>(ARG_WEATHER_DATE)
    private val id by argument<String>(ARG_WEATHER_ITEM_ID)

    val detailViewModel : DetailViewModel by viewModel { mapOf("id" to id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

        detailViewModel.uiData.observe(this, android.arch.lifecycle.Observer { detail ->
            if (detail != null) {
                displayDetail(detail)
            }
        })
        detailViewModel.getDetail(id)
    }

    fun displayDetail(weather: DailyForecastModel) {
        weatherTitle.text = getString(R.string.weather_title).format(address, now)
        weatherItemIcon.text = weather.icon
        weatherItemForecast.text = weather.forecastString
        weatherItemTemp.text = weather.temperatureString
    }
}
