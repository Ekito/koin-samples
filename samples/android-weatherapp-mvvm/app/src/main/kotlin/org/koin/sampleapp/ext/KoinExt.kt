package org.koin.sampleapp.ext

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import org.koin.android.architecture.ext.getViewModel

/**
 * Lazy get a ViewModel
 */
inline fun <reified T : ViewModel> FragmentActivity.viewModel() = lazy { getViewModel<T>() }

/**
 * Lazy get view model from Parent Activity
 */
inline fun <reified T : ViewModel> Fragment.viewModel() = lazy { activity.getViewModel<T>() }
