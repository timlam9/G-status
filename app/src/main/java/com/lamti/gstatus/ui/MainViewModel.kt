package com.lamti.gstatus.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lamti.gstatus.SpeedTester
import com.lamti.gstatus.models.SpeedTest
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val speedTester: SpeedTester = SpeedTester()): ViewModel() {

    val speedTest: StateFlow<SpeedTest> = speedTester.speedTest

    fun initializeSpeedTester(context: Context) {
        speedTester.initialize(context)
    }

    fun startSpeedTest(context: Context) {
        speedTester.startTest(context)
    }
}
