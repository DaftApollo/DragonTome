package com.example.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class StatEntry(
    var stat: Int = 0,
    var source: String = ""
)