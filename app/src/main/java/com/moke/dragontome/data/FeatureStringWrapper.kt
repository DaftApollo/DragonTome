package com.moke.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class FeatureStringWrapper(
    var value:String = "",
    var isGenerated:Boolean = false
)
