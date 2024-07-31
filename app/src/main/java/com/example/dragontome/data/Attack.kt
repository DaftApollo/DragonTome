package com.example.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class Attack(
    var name: String = "",
    var attackbonus:StatList = StatList(),
    var damage: String = ""
)
