package com.example.dragontome.data

import android.media.Image
import kotlin.time.TimeSource

data class Message(
    val userID: String = "",
    var text: String = "",
    val timeStamp: Long = System.currentTimeMillis()
)
