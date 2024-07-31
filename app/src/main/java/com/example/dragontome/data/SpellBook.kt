package com.example.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
data class SpellBook(
    var cantrips:List<Spell> = emptyList(),

    var levelOneSpells: List<Spell> = emptyList(),
    var levelOneSlotsMax: StatList = StatList(usesBaseStat = false),
    var levelOneSlots: IntWrapper = IntWrapper(value = 0),

    var levelTwoSpells: List<Spell> = emptyList(),
    var levelTwoSlotsMax: StatList = StatList(usesBaseStat = false),
    var levelTwoSlots: IntWrapper = IntWrapper(value = 0),

    var levelThreeSpells: List<Spell> = emptyList(),
    var levelThreeSlotsMax: StatList = StatList(usesBaseStat = false),
    var levelThreeSlots: IntWrapper = IntWrapper(value = 0),

    var levelFourSpells: List<Spell> = emptyList(),
    var levelFourSlotsMax: StatList = StatList(usesBaseStat = false),
    var levelFourSlots: IntWrapper = IntWrapper(value = 0),

    var levelFiveSpells: List<Spell> = emptyList(),
    var levelFiveSlotsMax: StatList = StatList(usesBaseStat = false),
    var levelFiveSlots: IntWrapper = IntWrapper(value = 0),

    var levelSixSpells: List<Spell> = emptyList(),
    var levelSixSlotsMax: StatList = StatList(usesBaseStat = false),
    var levelSixSlots: IntWrapper = IntWrapper(value = 0),

    var levelSevenSpells: List<Spell> = emptyList(),
    var levelSevenSlotsMax: StatList = StatList(usesBaseStat = false),
    var levelSevenSlots: IntWrapper = IntWrapper(value = 0),

    var levelEightSpells: List<Spell> = emptyList(),
    var levelEightSlotsMax: StatList = StatList(usesBaseStat = false),
    var levelEightSlots: IntWrapper = IntWrapper(value = 0),

    var levelNineSpells: List<Spell> = emptyList(),
    var levelNineSlotsMax: StatList = StatList(usesBaseStat = false),
    var levelNineSlots: IntWrapper = IntWrapper(value = 0),

    )
