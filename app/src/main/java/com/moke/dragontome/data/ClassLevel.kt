package com.moke.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class ClassLevel(
    var characterClass: CharacterClass = CharacterClass.ARTIFICER,
    var classLevel: Int = 0
)
