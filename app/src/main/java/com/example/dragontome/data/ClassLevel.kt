package com.example.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class ClassLevel(
    var characterClass: CharacterClass,
    var classLevel: Int
)
