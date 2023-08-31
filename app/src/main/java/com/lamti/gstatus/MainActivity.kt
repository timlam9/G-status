package com.lamti.gstatus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lamti.gstatus.ui.components.Speedometer
import com.lamti.gstatus.ui.components.RangeSlider
import com.lamti.gstatus.ui.converters.toSpeedometerValue
import com.lamti.gstatus.ui.theme.GStatusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GStatusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var speedometerValue by remember { mutableStateOf(0f) }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Speedometer(value = speedometerValue)
                        RangeSlider(
                            modifier = Modifier.padding(20.dp),
                            onValueChange = {
                                speedometerValue = it.toSpeedometerValue()
                            },
                            onValueChangeFinished = {}
                        )
                        Text(text = "$speedometerValue UI units", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}
