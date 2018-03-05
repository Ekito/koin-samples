package org.koin.sampleapp.view.search

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.koin.android.architecture.ext.viewModel
import org.koin.sampleapp.R
import org.koin.sampleapp.view.Arguments.ARG_ADDRESS
import org.koin.sampleapp.view.Arguments.ARG_WEATHER_DATE
import org.koin.sampleapp.view.result.ResultActivity
import java.util.*


/**
 * Search Weather View
 */
class SearchActivity : AppCompatActivity() {

    val myModel: SearchViewModel by viewModel()

    val s : String by lazy {
        println()
        "pouet" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("onCreate ...")

        // Start uiData weather
        searchButton.setOnClickListener {
            displayProgress()
            val address = getSearchText()
            myModel.searchWeather(address)
        }

        myModel.searchEvent.observe(this, android.arch.lifecycle.Observer { searchEvent ->
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
        myModel.uiData.observe(this, android.arch.lifecycle.Observer { uiData ->
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
        startActivity<ResultActivity>(
                ARG_WEATHER_DATE to Date(),
                ARG_ADDRESS to getSearchText())
    }

    fun onWeatherFailed(error: Throwable?) {
        Snackbar.make(searchLayout, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}
