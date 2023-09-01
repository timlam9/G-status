package com.lamti.gstatus

import android.content.Context
import com.speedchecker.android.sdk.Public.SpeedTestListener
import com.speedchecker.android.sdk.Public.SpeedTestResult
import com.speedchecker.android.sdk.SpeedcheckerSDK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpeedTester(private val context: Context): SpeedTestListener {

    private var _downloadSpeed = MutableStateFlow(0f)
    val downloadSpeed: StateFlow<Float> = _downloadSpeed.asStateFlow()

    init {
        SpeedcheckerSDK.init(context)
        SpeedcheckerSDK.SpeedTest.setOnSpeedTestListener(this)
    }

    fun startTest() {
        SpeedcheckerSDK.SpeedTest.startTest(context)
    }

    override fun onTestStarted() {
        TODO("Not yet implemented")
    }

    override fun onFetchServerFailed(p0: Int?) {
        TODO("Not yet implemented")
    }

    override fun onFindingBestServerStarted() {
        TODO("Not yet implemented")
    }

    override fun onTestFinished(p0: SpeedTestResult?) {
        TODO("Not yet implemented")
    }

    override fun onPingStarted() {
        TODO("Not yet implemented")
    }

    override fun onPingFinished(p0: Int, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun onDownloadTestStarted() {
        TODO("Not yet implemented")
    }

    override fun onDownloadTestProgress(progress: Int, speedMbs: Double, transferredMb: Double) {
        _downloadSpeed.update {
            speedMbs.toFloat()
        }
    }

    override fun onDownloadTestFinished(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun onUploadTestStarted() {
        TODO("Not yet implemented")
    }

    override fun onUploadTestProgress(p0: Int, p1: Double, p2: Double) {
        TODO("Not yet implemented")
    }

    override fun onUploadTestFinished(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun onTestWarning(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun onTestFatalError(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun onTestInterrupted(p0: String?) {
        TODO("Not yet implemented")
    }
}