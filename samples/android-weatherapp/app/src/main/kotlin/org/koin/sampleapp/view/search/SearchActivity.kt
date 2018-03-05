package org.koin.sampleapp.view.search

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import org.koin.sampleapp.R
import org.koin.sampleapp.di.Params.SEARCH_ACTIVITY
import org.koin.sampleapp.view.Arguments.ARG_ADDRESS
import org.koin.sampleapp.view.Arguments.ARG_WEATHER_DATE
import org.koin.sampleapp.view.result.ResultActivity
import java.util.*

/**
 * Search Weather View
 */
class SearchActivity : AppCompatActivity(), SearchContract.View {

    // Presenter
    override val presenter: SearchContract.Presenter by inject(parameters = mapOf(SEARCH_ACTIVITY to this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start search weather
        searchButton.setOnClickListener {
            presenter.getWeather(getSearchText())
        }
    }

    fun getSearchText() = searchEditText.text.trim().toString()

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun displayNormal() {
        searchProgress.visibility = View.GONE
        searchButton.visibility = View.VISIBLE
    }

    override fun displayProgress() {
        searchProgress.visibility = View.VISIBLE
        searchButton.visibility = View.GONE
    }

    override fun onWeatherSuccess() {
        // save address
        startActivity<ResultActivity>(
                ARG_WEATHER_DATE to Date(),
                ARG_ADDRESS to getSearchText())
    }

    override fun onWeatherFailed(error: Throwable) {
        Snackbar.make(this.currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}
