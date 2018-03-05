package org.koin.sampleapp.view.result

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
import org.koin.sampleapp.di.Params.RESULT_ACTIVITY
import org.koin.sampleapp.model.DailyForecastModel

class ResultListFragment : Fragment(), ResultListContract.View {

    private lateinit var weatherResultAdapter: ResultListAdapter

    val TAG = javaClass.simpleName

    override val presenter by inject<ResultListContract.Presenter>(parameters = mapOf(RESULT_ACTIVITY to this))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherList.layoutManager = LinearLayoutManager(context)
        weatherResultAdapter = ResultListAdapter(emptyList(), { weatherDetail ->
            presenter.selectWeatherDetail(weatherDetail)
        })
        weatherList.adapter = weatherResultAdapter
        presenter.getWeather()
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun displayWeather(weatherList: List<DailyForecastModel>) {
        weatherResultAdapter.list = weatherList
        weatherResultAdapter.notifyDataSetChanged()
    }

    override fun displayError(error: Throwable) {
        Snackbar.make(weatherList, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}