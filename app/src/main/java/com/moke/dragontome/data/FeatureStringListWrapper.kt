package com.moke.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class FeatureStringListWrapper(
    var stringList: List<FeatureStringWrapper> = emptyList()
)
