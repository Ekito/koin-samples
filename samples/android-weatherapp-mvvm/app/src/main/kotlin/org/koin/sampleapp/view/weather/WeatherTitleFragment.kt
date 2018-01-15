package org.koin.sampleapp.view.weather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_title.*
import org.koin.android.ext.android.property
import org.koin.sampleapp.R
import org.koin.sampleapp.di.WeatherAppProperties
import org.koin.sampleapp.ext.viewModel
import java.util.*

class WeatherTitleFragment : Fragment() {

    val TAG = this::class.java.simpleName

    // Get address
    private val address by property<String>(WeatherAppProperties.PROPERTY_ADDRESS)
    // get Last date
    private val now by property<Date>(WeatherAppProperties.PROPERTY_WEATHER_DATE)

    val model: WeatherResultViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherTitle.text = getString(R.string.weather_title).format(address, now)

        model.selectEvent.observe(this, android.arch.lifecycle.Observer { e ->
            Log.i(TAG, "got event : $e")
            //TODO handle clicked - display in title
        })
    }
}