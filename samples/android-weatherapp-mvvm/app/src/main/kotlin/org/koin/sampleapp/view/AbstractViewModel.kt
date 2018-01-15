package org.koin.sampleapp.view

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class AbstractViewModel : ViewModel() {

    val disposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}