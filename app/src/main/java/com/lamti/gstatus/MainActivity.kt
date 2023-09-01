package com.lamti.gstatus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lamti.gstatus.ui.components.Speedometer
import com.lamti.gstatus.ui.converters.toSpeedometerValue
import com.lamti.gstatus.ui.theme.GStatusTheme

class MainActivity : ComponentActivity() {

    private lateinit var speedTester: SpeedTester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        speedTester = SpeedTester(this@MainActivity)

        setContent {
            GStatusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var speedometerValue by remember { mutableStateOf(0f) }
                    val speed by speedTester.downloadSpeed.collectAsState()

                    LaunchedEffect(key1 = speed) {
                        speedometerValue = speed.toSpeedometerValue()
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Speedometer(value = speedometerValue)
                        Text(
                            text = "$speed Mbps | $speedometerValue UI units",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Button(onClick = { speedTester.startTest() }) {
                            Text(text = "Start")
                        }
                    }
                }
            }
        }
    }
}
