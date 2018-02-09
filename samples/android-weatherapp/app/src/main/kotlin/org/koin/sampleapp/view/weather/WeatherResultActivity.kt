package org.koin.sampleapp.view.weather

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import org.koin.android.ext.android.*
import org.koin.sampleapp.R
import org.koin.sampleapp.di.Context
import org.koin.sampleapp.di.WeatherAppProperties
import org.koin.sampleapp.di.WeatherAppProperties.PROPERTY_ADDRESS
import org.koin.sampleapp.di.WeatherAppProperties.PROPERTY_WEATHER_DATE
import org.koin.sampleapp.di.WeatherAppProperties.PROPERTY_WEATHER_ITEM_ID
import org.koin.sampleapp.view.detail.WeatherDetailActivity
import java.util.*

/**
 * Weather View
 */
class WeatherResultActivity : AppCompatActivity(), WeatherResultContract.View {

    override val presenter by inject<WeatherResultContract.Presenter>()

    val date: Date by argument(WeatherAppProperties.PROPERTY_WEATHER_DATE)
    val address: String by argument(WeatherAppProperties.PROPERTY_ADDRESS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val weatherTitleFragment = WeatherTitleFragment()
                .withArguments(PROPERTY_WEATHER_DATE to date, PROPERTY_ADDRESS to address)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.weather_title, weatherTitleFragment)
                .commit()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.weather_list, WeatherListFragment())
                .commit()
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
    }

    override fun onPause() {
        presenter.stop()
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        releaseContext(Context.WEATHER_LIST)
    }

    override fun onDetailSaved(id: String) {
        startActivity<WeatherDetailActivity> {
            withArguments(
                    PROPERTY_WEATHER_DATE to date,
                    PROPERTY_ADDRESS to address,
                    PROPERTY_WEATHER_ITEM_ID to id)
        }
    }

    override fun displayError(error: Throwable) {
        Snackbar.make(this.currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}
