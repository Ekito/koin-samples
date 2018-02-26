package org.koin.sampleapp.view.result

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import org.koin.android.architecture.ext.viewModel
import org.koin.sampleapp.R
import org.koin.sampleapp.util.ext.argument
import org.koin.sampleapp.util.ext.withArguments
import org.koin.sampleapp.view.Arguments.ARG_ADDRESS
import org.koin.sampleapp.view.Arguments.ARG_WEATHER_DATE
import org.koin.sampleapp.view.Arguments.ARG_WEATHER_ITEM_ID
import org.koin.sampleapp.view.detail.DetailActivity
import java.util.*

/**
 * Weather Result View
 */
class ResultActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    val model: ResultViewModel by viewModel()

    val date: Date by argument(ARG_WEATHER_DATE)
    val address: String by argument(ARG_ADDRESS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        // Listen for weather id selected
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
        val weatherResultTitleFragment = ResultHeaderFragment()
                .withArguments(ARG_WEATHER_DATE to date,
                        ARG_ADDRESS to address)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.weather_title, weatherResultTitleFragment)
                .commit()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.weather_list, ResultListFragment())
                .commit()

    }

    fun onWeatherSelected(id: String) {
        startActivity<DetailActivity>(
                ARG_WEATHER_DATE to date,
                ARG_ADDRESS to address,
                ARG_WEATHER_ITEM_ID to id)
    }

    fun displayError(error: Throwable?) {
        Snackbar.make(currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}
