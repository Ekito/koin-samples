package org.koin.sampleapp

import android.app.Application
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.WeathericonsModule
import org.koin.ContextCallback
import org.koin.android.ext.android.startKoin
import org.koin.sampleapp.di.localAndroidDatasourceModule
import org.koin.sampleapp.di.weatherApp
import org.koin.standalone.StandAloneContext.registerContextCallBack

/**
 * Main Application
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin(this, weatherApp + localAndroidDatasourceModule)

        // Listen with ContextCallback
        registerContextCallBack(object : ContextCallback {

            // Notified on context dropped
            override fun onContextReleased(contextName: String) {
                println("Context $contextName has been dropped")
            }

        })

        Iconify.with(WeathericonsModule())
    }
}
