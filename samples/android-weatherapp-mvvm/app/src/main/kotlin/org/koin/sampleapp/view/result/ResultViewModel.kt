package org.koin.sampleapp.view.result

import android.arch.lifecycle.MutableLiveData
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.ext.with
import org.koin.sampleapp.view.AbstractViewModel
import org.koin.sampleapp.view.SingleLiveEvent

/**
 * Weather Presenter
 */
class ResultViewModel(private val weatherRepository: WeatherRepository, private val scheduler: SchedulerProvider) : AbstractViewModel() {

    val uiData = MutableLiveData<ResultUIModel>()
    val selectEvent = SingleLiveEvent<ResultSelectEvent>()

    fun getWeatherList() {
        launch {
            weatherRepository.getWeather().with(scheduler)
                    .subscribe({ list ->
                        uiData.value = ResultUIModel(list)
                    }, { e ->
                        uiData.value = ResultUIModel(error = e)
                    })
        }
    }

    fun selectWeatherDetail(id: String) {
        selectEvent.value = ResultSelectEvent(idSelected = id)
    }
}

data class ResultUIModel(val list: List<DailyForecastModel> = emptyList(), val error: Throwable? = null)
data class ResultSelectEvent(val idSelected: String? = null, val error: Throwable? = null)