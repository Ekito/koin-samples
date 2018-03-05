package org.koin.sampleapp.view.result

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_list.*
import org.koin.android.architecture.ext.viewModel
import org.koin.sampleapp.R
import org.koin.sampleapp.model.DailyForecastModel

class ResultListFragment : Fragment() {

    private lateinit var weatherResultAdapter: ResultListAdapter

    val TAG = javaClass.simpleName

    val model: ResultViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "myModel : $model")

        // Listen Weather list
        model.uiData.observe(this, android.arch.lifecycle.Observer<ResultUIModel> {
            if (it != null) {
                val weatherList = it.list
                if (weatherList != weatherResultAdapter.list && weatherList.isNotEmpty()) {
                    displayWeather(weatherList)
                } else if (it.error != null) {
                    displayError(it.error)
                }
            }
        })

        // Bind onItemClicked with adapter
        weatherResultAdapter = ResultListAdapter(emptyList(), onItemClicked())
        weatherList.layoutManager = LinearLayoutManager(context)
        weatherList.adapter = weatherResultAdapter

        model.getWeatherList()
    }

    private fun onItemClicked(): (DailyForecastModel) -> Unit {
        return { weatherDetail ->
            // notify weather to show
            model.selectWeatherDetail(weatherDetail.id)
        }
    }

    fun displayWeather(weatherList: List<DailyForecastModel>) {
        weatherResultAdapter.list = weatherList
        weatherResultAdapter.notifyDataSetChanged()
    }

    fun displayError(error: Throwable?) {
        Snackbar.make(weatherList, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}