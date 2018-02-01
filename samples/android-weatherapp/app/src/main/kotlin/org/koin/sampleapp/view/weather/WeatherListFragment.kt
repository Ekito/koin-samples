package org.koin.sampleapp.view.weather

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_list.*
import org.koin.android.ext.android.inject
import org.koin.sampleapp.R
import org.koin.sampleapp.model.DailyForecastModel

class WeatherListFragment : Fragment(), WeatherListResultContract.View {

    private lateinit var weatherResultAdapter: WeatherResultAdapter

    val TAG = javaClass.simpleName

    override val presenter by inject<WeatherListResultContract.Presenter>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherList.layoutManager = LinearLayoutManager(context)
        weatherResultAdapter = WeatherResultAdapter(emptyList(), { weatherDetail ->
            presenter.selectWeatherDetail(weatherDetail)
        })
        weatherList.adapter = weatherResultAdapter
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
        presenter.getWeather()
    }

    override fun onPause() {
        presenter.stop()
        super.onPause()
    }

    override fun displayWeather(weatherList: List<DailyForecastModel>) {
        weatherResultAdapter.list = weatherList
        weatherResultAdapter.notifyDataSetChanged()
    }

    override fun displayError(error: Throwable) {
        Snackbar.make(this.activity.currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}