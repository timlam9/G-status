package com.lamti.gstatus.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lamti.gstatus.models.SpeedTest
import com.lamti.gstatus.ui.components.Speedometer
import com.lamti.gstatus.ui.converters.toSpeedometerValue

@Composable
fun SpeedometerScreen(speedTest: SpeedTest) {
    var speedometerValue by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = speedTest.downloadSpeed) {
        speedometerValue = speedTest.downloadSpeed.value.toSpeedometerValue()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Speedometer(value = speedometerValue)
        Text(
            text = "${speedTest.downloadSpeed.value} Mbps | $speedometerValue UI units",
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}
