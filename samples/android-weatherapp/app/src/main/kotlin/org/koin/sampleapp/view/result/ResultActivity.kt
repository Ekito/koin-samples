package org.koin.sampleapp.view.result

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.releaseContext
import org.koin.sampleapp.R
import org.koin.sampleapp.di.Context
import org.koin.sampleapp.util.ext.argument
import org.koin.sampleapp.util.ext.withArguments
import org.koin.sampleapp.view.Arguments.ARG_ADDRESS
import org.koin.sampleapp.view.Arguments.ARG_WEATHER_DATE
import org.koin.sampleapp.view.Arguments.ARG_WEATHER_ITEM_ID
import org.koin.sampleapp.view.detail.DetailActivity
import java.util.*

/**
 * Weather Result View
 */
class ResultActivity : AppCompatActivity(), ResultContract.View {

    override val presenter by inject<ResultContract.Presenter>()

    val date: Date by argument(ARG_WEATHER_DATE)
    val address: String by argument(ARG_ADDRESS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val weatherTitleFragment = ResultHeaderFragment()
            .withArguments(
                ARG_WEATHER_DATE to date,
                ARG_ADDRESS to address
            )

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.weather_title, weatherTitleFragment)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.weather_list, ResultListFragment())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        presenter.view = this
    }

    override fun onStop() {
        presenter.stop()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseContext(Context.WEATHER_LIST)
    }

    override fun onDetailSaved(id: String) {
        startActivity<DetailActivity>(
            ARG_WEATHER_DATE to date,
            ARG_ADDRESS to address,
            ARG_WEATHER_ITEM_ID to id
        )
    }

    override fun displayError(error: Throwable) {
        Snackbar.make(this.currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }
}
