package org.koin.sampleapp.view.weather

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.repository.json.getDailyForecasts
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.rx.with

/**
 * Weather Presenter
 */
class WeatherResultViewModel(private val weatherRepository: WeatherRepository, private val scheduler: SchedulerProvider) : ViewModel() {

    val currentSearch = MutableLiveData<WeatherResultUIModel>()
    private val disposables = CompositeDisposable()
    private lateinit var cacheList: List<DailyForecastModel>

    init {
        getWeatherList()
    }

    fun getWeatherList() {
        disposables.add(weatherRepository.getWeather().map { it.getDailyForecasts() }.with(scheduler)
                .subscribe
                ({ list ->
                    currentSearch.value = WeatherResultUIModel(list)
                    cacheList = list
                }, { e ->
                    currentSearch.value = WeatherResultUIModel(error = e)
                })
        )
    }

    fun selectWeatherDetail(detail: DailyForecastModel) {
        disposables.add(weatherRepository.selectWeatherDetail(detail).with(scheduler).subscribe {
            currentSearch.value = WeatherResultUIModel(emptyList(), true)
            currentSearch.value = WeatherResultUIModel(cacheList)
        })
    }

    override fun onCleared() {
        disposables.clear()
    }
}


data class WeatherResultUIModel(val list: List<DailyForecastModel> = emptyList(), val selected: Boolean = false, val error: Throwable? = null)