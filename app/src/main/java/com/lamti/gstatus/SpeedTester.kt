package com.lamti.gstatus

import android.content.Context
import android.util.Log
import com.lamti.gstatus.models.InternetSpeed
import com.lamti.gstatus.models.SpeedTest
import com.lamti.gstatus.models.TestStatus
import com.speedchecker.android.sdk.Public.SpeedTestListener
import com.speedchecker.android.sdk.Public.SpeedTestResult
import com.speedchecker.android.sdk.SpeedcheckerSDK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpeedTester : SpeedTestListener {

    private var _speedTest = MutableStateFlow(SpeedTest())
    val speedTest: StateFlow<SpeedTest> = _speedTest.asStateFlow()

    fun initialize(context: Context) {
        SpeedcheckerSDK.init(context)
        SpeedcheckerSDK.SpeedTest.setOnSpeedTestListener(this)
    }

    fun startTest(context: Context) {
        // Reset state
        _speedTest.update {
            it.copy(
                downloadSpeed = InternetSpeed(),
                uploadSpeed = InternetSpeed(),
                ping = 0,
                jitter = 0,
                isPingStarted = false,
                isDownloadStarted = false,
                isUploadStarted = false,
                status = TestStatus.NOT_STARTED,
            )
        }
        SpeedcheckerSDK.SpeedTest.startTest(context)
    }

    override fun onTestStarted() {
        Log.d("SPEED_TEST", "Test started")
        _speedTest.update { it.copy(status = TestStatus.RUNNING) }
    }

    override fun onFetchServerFailed(errorCode: Int) {
        Log.d("SPEED_TEST", "Fetch server failed")
    }

    override fun onFindingBestServerStarted() {
        Log.d("SPEED_TEST", "Finding best server started")
    }

    override fun onTestFinished(speedTestResult: SpeedTestResult) {
        Log.d("SPEED_TEST", "Test finished")
        _speedTest.update {
            it.copy(
                status = TestStatus.FINISHED,
                isPingStarted = false,
                isDownloadStarted = false,
                isUploadStarted = false,
            )
        }
    }

    override fun onPingStarted() {
        Log.d("SPEED_TEST", "Ping started")
        _speedTest.update { it.copy(isPingStarted = true) }
    }

    override fun onPingFinished(ping: Int, jitter: Int) {
        Log.d("SPEED_TEST", "Ping finished with ping: $ping, jitter: $jitter")
        _speedTest.update {
            it.copy(
                ping = ping,
                jitter = jitter,
                isPingStarted = false,
            )
        }
    }

    override fun onDownloadTestStarted() {
        Log.d("SPEED_TEST", "Download test started")
        _speedTest.update { it.copy(isDownloadStarted = true) }
    }

    override fun onDownloadTestProgress(progress: Int, speedMbs: Double, transferredMb: Double) {
        _speedTest.update {
            it.copy(
                downloadSpeed = InternetSpeed(
                    value = speedMbs.toFloat(),
                    progress = progress
                ),
            )
        }
    }

    override fun onDownloadTestFinished(value: Double) {
        Log.d("SPEED_TEST", "Download test finished")
        _speedTest.update {
            it.copy(
                isDownloadStarted = false
            )
        }
    }

    override fun onUploadTestStarted() {
        Log.d("SPEED_TEST", "Upload test started")
        _speedTest.update { it.copy(isUploadStarted = true) }
    }

    override fun onUploadTestProgress(progress: Int, speedMbs: Double, transferredMb: Double) {
        _speedTest.update {
            it.copy(
                uploadSpeed = InternetSpeed(
                    value = speedMbs.toFloat(),
                    progress = progress
                ),
            )
        }
    }

    override fun onUploadTestFinished(value: Double) {
        Log.d("SPEED_TEST", "Upload test finished with value: $value")
    }

    override fun onTestWarning(warning: String?) {
        Log.d("SPEED_TEST", "Test warning: $warning")
    }

    override fun onTestFatalError(error: String?) {
        Log.d("SPEED_TEST", "Test fatal error: $error")
    }

    override fun onTestInterrupted(reason: String?) {
        Log.d("SPEED_TEST", "Test interrupted: $reason")
    }
}