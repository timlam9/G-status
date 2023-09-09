package com.lamti.gstatus.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lamti.gstatus.models.SpeedTest

@Composable
fun SpeedometerScreen(speedometerValue: Float, speedTest: SpeedTest) {
    com.lamti.gstatus.ui.components.Speedometer(value = speedometerValue)
    Text(
        text = "${speedTest.downloadSpeed.value} Mbps | $speedometerValue UI units",
        style = MaterialTheme.typography.labelLarge
    )
    Spacer(modifier = Modifier.height(40.dp))
}
