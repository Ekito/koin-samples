package org.koin.sampleapp.view.weather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.koin.sampleapp.R

/**
 * Weather View
 */
class WeatherResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        supportFragmentManager.beginTransaction()
                .add(R.id.weather_title, WeatherTitleFragment(), "title")
                .commit()
    }
}
