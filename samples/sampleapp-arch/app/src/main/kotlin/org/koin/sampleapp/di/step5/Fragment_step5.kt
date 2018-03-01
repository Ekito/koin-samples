/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.koin.sampleapp.di.step5


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.android.codelabs.lifecycle.R

/**
 * Shows a SeekBar that is synced with a value in a ViewModel.
 */
class Fragment_step5 : Fragment() {

    private var mSeekBar: SeekBar? = null

    private var mSeekBarViewModel: SeekBarViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_step5, container, false)
        mSeekBar = root.findViewById(R.id.seekBar)

        mSeekBarViewModel = ViewModelProviders.of(activity!!).get(SeekBarViewModel::class.java)

        subscribeSeekBar()

        return root
    }

    private fun subscribeSeekBar() {

        // Update the ViewModel when the SeekBar is changed.
        mSeekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    Log.d("Step5", "Progress changed!")
                    mSeekBarViewModel!!.seekbarValue.value = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // Update the SeekBar when the ViewModel is changed.
        mSeekBarViewModel!!.seekbarValue.observe(activity!!, Observer<Int> { value ->
            if (value != null) {
                mSeekBar!!.progress = value
            }
        })
    }
}

