package com.lamti.gstatus.ui.screens

import androidx.compose.runtime.Composable
import com.lamti.gstatus.models.SpeedTest
import com.lamti.gstatus.models.TestStatus

@Composable
fun MainScreen(speedTest: SpeedTest, onStartClick: () -> Unit) {
    when {
        speedTest.status == TestStatus.NOT_STARTED -> StartScreen(onStartClick = onStartClick)
        speedTest.status == TestStatus.RUNNING && speedTest.downloadSpeed.progress == 0 -> LoadingScreen()
        else -> SpeedometerScreen(speedTest = speedTest, onRestartClick = onStartClick)
    }
}
