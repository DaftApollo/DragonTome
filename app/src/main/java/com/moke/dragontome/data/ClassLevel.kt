package com.moke.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class ClassLevel(
    var characterClass: CharacterClass = CharacterClass.CUSTOM,
    var characterSubClass: CharacterSubClass = CharacterSubClass.CUSTOM,
    var classLevel: Int = 0
)
