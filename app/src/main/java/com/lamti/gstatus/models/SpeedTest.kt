package com.lamti.gstatus.models

data class SpeedTest(
    val downloadSpeed: InternetSpeed = InternetSpeed(),
    val uploadSpeed: InternetSpeed = InternetSpeed(),
    val isPingStarted: Boolean = false,
    val isDownloadStarted: Boolean = false,
    val isUploadStarted: Boolean = false,
    val isTestRunning: Boolean = false
)
