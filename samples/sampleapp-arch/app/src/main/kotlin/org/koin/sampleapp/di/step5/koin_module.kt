package org.koin.sampleapp.di.step5

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext


val module5 = applicationContext {
    viewModel { SeekBarViewModel() }
}