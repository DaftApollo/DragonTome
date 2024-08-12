package com.example.dragontome.data

data class OnlineCharacterSheetHolder(
    var characterSheet: CharacterSheet = CharacterSheet(),
    var owner:String = "",
    var hidden:Boolean = false
)
