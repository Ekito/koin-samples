package org.koin.sampleapp.view

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.sampleapp.util.mvp.BasePresenter
import org.koin.sampleapp.util.mvp.BaseView

abstract class AbstractPresenter<V : BaseView<P>, out P : BasePresenter<V>> : BasePresenter<V> {

    override lateinit var view: V

    val disposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    override fun stop() {
        disposables.clear()
    }
}