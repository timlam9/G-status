package com.lamti.gstatus.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lamti.gstatus.models.SpeedTest
import com.lamti.gstatus.ui.converters.toSpeedometerValue

@Composable
fun MainScreen(speedTest: SpeedTest, onStartClick: () -> Unit) {
    var speedometerValue by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = speedTest.downloadSpeed) {
        speedometerValue = speedTest.downloadSpeed.value.toSpeedometerValue()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when {
            speedTest.isTestRunning -> if (speedTest.isDownloadStarted) SpeedometerScreen(
                speedometerValue,
                speedTest
            ) else LoadingScreen()

            else -> StartScreen(onStartClick)
        }
    }
}
