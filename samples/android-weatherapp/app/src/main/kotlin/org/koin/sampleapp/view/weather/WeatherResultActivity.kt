package org.koin.sampleapp.view.weather

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.releaseContext
import org.koin.sampleapp.R
import org.koin.sampleapp.di.Context
import org.koin.sampleapp.view.detail.WeatherDetailActivity

/**
 * Weather View
 */
class WeatherResultActivity : AppCompatActivity(), WeatherResultContract.View {

    override val presenter by inject<WeatherResultContract.Presenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.weather_title, WeatherTitleFragment())
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

    override fun onDetailSaved() {
        startActivity(Intent(this, WeatherDetailActivity::class.java))
    }

    override fun displayError(error: Throwable) {
        Snackbar.make(this.currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}
