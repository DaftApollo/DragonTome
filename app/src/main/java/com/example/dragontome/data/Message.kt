package com.example.dragontome.data

import android.media.Image
import kotlin.time.TimeSource

data class Message(
    val user: String,
    val text: String,
    val attachment: Image? = null,
    val timeStamp: Long = System.currentTimeMillis()
)
