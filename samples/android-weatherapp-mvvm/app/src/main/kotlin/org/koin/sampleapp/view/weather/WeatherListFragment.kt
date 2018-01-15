package org.koin.sampleapp.view.weather

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_list.*
import org.koin.android.architecture.ext.getViewModel
import org.koin.sampleapp.R
import org.koin.sampleapp.ext.viewModel
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.view.detail.WeatherDetailActivity

class WeatherListFragment : Fragment() {

    private lateinit var weatherResultAdapter: WeatherResultAdapter

    val model: WeatherResultViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_weather_title, container, false)
    }

    override fun onStart() {
        super.onStart()
        
        val model = getViewModel<WeatherResultViewModel>()
        model.weatherList.observe(this, android.arch.lifecycle.Observer<WeatherResultUIModel> {
            if (it != null) {
                val weatherList = it.list
                if (weatherList != weatherResultAdapter.list && weatherList.isNotEmpty()) {
                    displayWeather(weatherList)
                } else if (it.error != null) {
                    displayError(it.error)
                }
            }
        })
        model.selectEvent.observe(this, android.arch.lifecycle.Observer {
            if (it != null) {
                if (it.isSelected) {
                    onDetailSaved()
                } else if (it.error != null) {
                    displayError(it.error)
                }
            }
        })
        weatherResultAdapter = WeatherResultAdapter(emptyList(), { weatherDetail ->
            // save date & weather detail
            model.selectWeatherDetail(weatherDetail)
        })
        weatherList.layoutManager = LinearLayoutManager(context)
        weatherList.adapter = weatherResultAdapter
    }

    fun displayWeather(weatherList: List<DailyForecastModel>) {
        weatherResultAdapter.list = weatherList
        weatherResultAdapter.notifyDataSetChanged()
    }

    fun onDetailSaved() {
        activity.startActivity(Intent(this.context, WeatherDetailActivity::class.java))
    }

    fun displayError(error: Throwable?) {
        Snackbar.make(this.activity.currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}