package org.koin.sampleapp.view.weather

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.setProperty
import org.koin.sampleapp.R
import org.koin.sampleapp.di.WeatherAppProperties.PROPERTY_WEATHER_ITEM_ID
import org.koin.sampleapp.view.detail.WeatherDetailActivity

/**
 * Weather View
 */
class WeatherResultActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    val model: WeatherResultViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        // Listen for weather id selected
        Log.i(TAG, "model : $model")
        model.selectEvent.observe(this, android.arch.lifecycle.Observer {
            if (it != null) {
                if (it.idSelected != null) {
                    onWeatherSelected(it.idSelected)
                } else if (it.error != null) {
                    displayError(it.error)
                }
            }
        })

        // Launch fragments
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.weather_title, WeatherResultTitleFragment())
                .commit()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.weather_list, WeatherResultListFragment())
                .commit()

    }

    fun onWeatherSelected(id: String) {
        setProperty(PROPERTY_WEATHER_ITEM_ID, id)
        startActivity(Intent(this, WeatherDetailActivity::class.java))
    }

    fun displayError(error: Throwable?) {
        Snackbar.make(currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}
