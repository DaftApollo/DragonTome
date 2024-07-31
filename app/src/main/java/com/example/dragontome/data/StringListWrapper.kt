package com.example.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class StringListWrapper(
    var stringList: List<StringWrapper> = emptyList()
)
