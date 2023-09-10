package com.lamti.gstatus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.lamti.gstatus.ui.MainViewModel
import com.lamti.gstatus.ui.screens.MainScreen
import com.lamti.gstatus.ui.theme.GStatusTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GStatusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val speedTest by viewModel.speedTest.collectAsState()

                    MainScreen(speedTest = speedTest) {
                        viewModel.startSpeedTest(this@MainActivity)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.initializeSpeedTester(this@MainActivity)
    }
}
