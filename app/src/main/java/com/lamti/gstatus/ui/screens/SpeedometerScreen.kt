package com.lamti.gstatus.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.gstatus.models.InternetSpeed
import com.lamti.gstatus.models.SpeedTest
import com.lamti.gstatus.models.TestStatus
import com.lamti.gstatus.ui.components.InfoCard
import com.lamti.gstatus.ui.components.SpeedCard
import com.lamti.gstatus.ui.components.Speedometer
import com.lamti.gstatus.ui.converters.toSpeedometerValue
import com.lamti.gstatus.ui.theme.GStatusTheme

@Composable
fun SpeedometerScreen(speedTest: SpeedTest, onRestartClick: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val speedValueOffset = -(screenWidth / 3)

    var speedometerValue by remember { mutableStateOf(0f) }
    var displayedSpeed by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = speedTest.downloadSpeed, key2 = speedTest.uploadSpeed) {
        displayedSpeed = if (speedTest.uploadSpeed.progress > 0) {
            speedTest.uploadSpeed.value
        } else {
            speedTest.downloadSpeed.value
        }
        speedometerValue = displayedSpeed.toSpeedometerValue()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            SpeedCard(
                speed = speedTest.downloadSpeed.value.toString(),
                title = "Download",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(20.dp))
            SpeedCard(
                speed = speedTest.uploadSpeed.value.toString(),
                title = "Upload",
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            InfoCard(speedTest.ping.toString())
            InfoCard("20")
            InfoCard("30")
            InfoCard(speedTest.jitter.toString())
        }
        Spacer(modifier = Modifier.height((screenWidth / 3) - screenWidth / 8))
        Speedometer(value = speedometerValue)
        Text(
            text = "$displayedSpeed Mbps",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.offset(y = speedValueOffset),
        )
        AnimatedVisibility(visible = speedTest.status == TestStatus.FINISHED) {
            Button(onClick = onRestartClick) {
                Text(text = "Restart")
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun SpeedometerScreenPreview() {
    GStatusTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SpeedometerScreen(
                speedTest = SpeedTest(
                    downloadSpeed = InternetSpeed(value = 80.0f, progress = 100),
                    uploadSpeed = InternetSpeed(value = 20.0f, progress = 100),
                    isPingStarted = true,
                    isDownloadStarted = false,
                    isUploadStarted = true,
                    status = TestStatus.RUNNING
                )
            )
        }
    }
}
