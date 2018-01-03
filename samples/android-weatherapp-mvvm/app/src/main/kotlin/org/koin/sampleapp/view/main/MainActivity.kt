package org.koin.sampleapp.view.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.ext.android.property
import org.koin.android.ext.android.setProperty
import org.koin.sampleapp.R
import org.koin.sampleapp.di.WeatherAppProperties.PROPERTY_ADDRESS
import org.koin.sampleapp.di.WeatherAppProperties.PROPERTY_WEATHER_DATE
import org.koin.sampleapp.view.weather.WeatherResultActivity
import java.util.*


/**
 * Weather View
 */
class MainActivity : AppCompatActivity() {

    val defaultAddress by property(PROPERTY_ADDRESS, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText.setText(defaultAddress)

        val model = getViewModel<MainViewModel>()

        // Start currentSearch weather
        searchButton.setOnClickListener {
            displayProgress()
            val address = getSearchText()
            setProperty(PROPERTY_ADDRESS, address)
            model.searchWeather(address)
        }

        model.weatherSearch.observe(this, android.arch.lifecycle.Observer<MainUIModel> {
            if (it != null) {
                if (it.isLoading) {
                    displayProgress()
                } else {
                    displayNormal()
                    if (it.isSuccess) {
                        onWeatherSuccess()
                    } else if (it.error != null) {
                        onWeatherFailed(it.error)
                    }
                }
                searchEditText.setText(it.searchText)
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
        setProperty(PROPERTY_WEATHER_DATE, Date())

        val intent = Intent(this, WeatherResultActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    fun onWeatherFailed(error: Throwable?) {
        Snackbar.make(searchLayout, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}
