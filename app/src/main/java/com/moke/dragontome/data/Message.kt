package com.moke.dragontome.data

data class Message(
    val userID: String = "",
    var text: String = "",
    val timeStamp: Long = System.currentTimeMillis(),
    var isRoll:Boolean = false
)
