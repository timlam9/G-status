package com.lamti.gstatus.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun StepsRangeSlider(
    modifier: Modifier = Modifier,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
) {
    var sliderPosition by remember { mutableStateOf(0f) }
    Text(text = sliderPosition.toString(), style = MaterialTheme.typography.labelLarge)
    Slider(
        modifier = modifier,
        value = sliderPosition,
        onValueChange = {
            onValueChange(it)
            sliderPosition = it
        },
        steps = 9,
        valueRange = 0f..1000f,
        onValueChangeFinished = onValueChangeFinished,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.secondary,
            activeTrackColor = MaterialTheme.colorScheme.secondary
        )
    )
}
