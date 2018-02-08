package org.koin.sampleapp.view.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.properties
import org.koin.android.ext.android.startActivity
import org.koin.sampleapp.R
import org.koin.sampleapp.di.WeatherAppProperties.PROPERTY_ADDRESS
import org.koin.sampleapp.di.WeatherAppProperties.PROPERTY_WEATHER_DATE
import org.koin.sampleapp.view.weather.WeatherResultActivity
import java.util.*


/**
 * Weather View
 */
class MainActivity : AppCompatActivity() {

    val model: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start weatherList weather
        searchButton.setOnClickListener {
            displayProgress()
            val address = getSearchText()
            model.searchWeather(address)
        }

        model.searchEvent.observe(this, android.arch.lifecycle.Observer { searchEvent ->
            if (searchEvent != null) {
                if (searchEvent.isLoading) {
                    displayProgress()
                } else {
                    displayNormal()
                    if (searchEvent.isSuccess) {
                        onWeatherSuccess()
                    } else if (searchEvent.error != null) {
                        onWeatherFailed(searchEvent.error)
                    }
                }
            }
        })
        model.uiData.observe(this, android.arch.lifecycle.Observer { uiData ->
            if (uiData != null) {
                val searchText = uiData.searchText
                if (searchText != null) {
                    searchEditText.setText(searchText)
                }
            }
        })
    }

    fun getSearchText() = searchEditText.text.trim().toString()

    fun displayNormal() {
        searchProgress.visibility = View.GONE
        searchButton.visibility = View.VISIBLE
    }

    fun displayProgress() {
        searchProgress.visibility = View.VISIBLE
        searchButton.visibility = View.GONE
    }

    fun onWeatherSuccess() {
        // save properties
        startActivity<WeatherResultActivity> {
            properties(
                    PROPERTY_WEATHER_DATE to Date(),
                    PROPERTY_ADDRESS to getSearchText()
            )
        }
    }

    fun onWeatherFailed(error: Throwable?) {
        Snackbar.make(searchLayout, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}
