package com.lamti.gstatus.models

data class SpeedTest(
    val downloadSpeed: InternetSpeed = InternetSpeed(),
    val uploadSpeed: InternetSpeed = InternetSpeed(),
    val ping: Int = 0,
    val jitter: Int = 0,
    val isPingStarted: Boolean = false,
    val isDownloadStarted: Boolean = false,
    val isUploadStarted: Boolean = false,
    val status: TestStatus = TestStatus.NOT_STARTED,
)
