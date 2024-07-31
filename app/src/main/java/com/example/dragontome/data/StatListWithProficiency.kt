package com.example.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class StatListWithProficiency(
    var proficiency: Boolean = false,
    var statList: StatList = StatList()
)
