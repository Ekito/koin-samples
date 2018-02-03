package org.koin.sampleapp.view.weather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_title.*
import org.koin.android.ext.android.property
import org.koin.sampleapp.R
import org.koin.sampleapp.di.WeatherAppProperties
import java.util.*

class WeatherResultTitleFragment : Fragment() {

    val TAG = javaClass.simpleName

    // Get address
    private val address by property<String>(WeatherAppProperties.PROPERTY_ADDRESS)
    // get Last date
    private val now by property<Date>(WeatherAppProperties.PROPERTY_WEATHER_DATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_weather_title, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherTitle.text = getString(R.string.weather_title).format(address, now)
    }
}