package org.koin.sampleapp.view.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_weather_detail.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.property
import org.koin.sampleapp.R
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.view.main.PROPERTY_ADDRESS
import org.koin.sampleapp.view.weather.PROPERTY_WEATHER_DATE
import java.util.*

/**
 * Weather Detail View
 */
class WeatherDetailActivity : AppCompatActivity(), WeatherDetailContract.View {

    // Get all needed data
    private val address by property<String>(PROPERTY_ADDRESS)
    private val now by property<Date>(PROPERTY_WEATHER_DATE)

    override val presenter: WeatherDetailContract.Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
        presenter.getDetail()
    }

    override fun onPause() {
        super.onPause()
        presenter.stop()
    }

    override fun displayDetail(weather: DailyForecastModel) {
        weatherTitle.text = getString(R.string.weather_title).format(address, now)
        weatherItemIcon.text = weather.icon
        weatherItemForecast.text = weather.forecastString
        weatherItemTemp.text = weather.temperatureString
    }
}
