package org.koin.sampleapp.ext

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import org.koin.android.architecture.ext.getViewModel

/**
 * Lazy get a ViewModel - for Activity
 */
inline fun <reified T : ViewModel> FragmentActivity.viewModel() = lazy { getViewModel<T>() }

/**
 * Lazy get view model
 * @param fromActivity - reuse ViewModel from parent Activity
 */
inline fun <reified T : ViewModel> Fragment.viewModel(fromActivity: Boolean = true) = lazy { if (fromActivity) activity.getViewModel<T>() else getViewModel() }
