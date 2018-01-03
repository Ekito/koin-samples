package org.koin.sampleapp.view.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.rx.with

/**
 * Weather Presenter
 */
class WeatherDetailViewModel(private val weatherRepository: WeatherRepository, private val scheduler: SchedulerProvider) : ViewModel() {

    private var disposable: Disposable? = null
    val detail = MutableLiveData<DailyForecastModel>()

    init {
        getDetail()
    }

    private fun getDetail() {
        disposable = weatherRepository.getSelectedWeatherDetail().with(scheduler)
                .subscribe(
                        { d -> detail.value = d },
                        { e -> println("got error : $e") })
    }

    override fun onCleared() {
        disposable?.dispose()
    }
}