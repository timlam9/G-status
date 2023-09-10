package com.lamti.gstatus.ui.screens

import androidx.compose.runtime.Composable
import com.lamti.gstatus.models.SpeedTest

@Composable
fun MainScreen(speedTest: SpeedTest, onStartClick: () -> Unit) {
    when {
        speedTest.isTestRunning -> if (speedTest.isDownloadStarted) SpeedometerScreen(speedTest) else LoadingScreen()
        else -> StartScreen(onStartClick)
    }
}
