package com.example.dragontome.data

import kotlinx.serialization.Serializable

enum class CharacterClass(){
                           ARTIFICER,
    BARBARIAN,
    BARD,
    BLOODHUNTER,
    CLERIC,
    DRUID,
    FIGHTER,
    MONK,
    PALADIN,
    RANGER,
    ROGUE,
    SORCERER,
    WARLOCK,
    WIZARD
}

enum class CharacterStat(){
    STRENGTH,
    DEXTERITY,
    CONSTITUTION,
    INTELLIGENCE,
    WISDOM,
    CHARISMA
}

@Serializable
data class CharacterSheet(
    //Basic Info
    var charName: String = "",
    var race: String = "",
    var background: String = "",
    var characterAlignment: String = "",
    var experiencePoints: Int = 0,

    //Character Class is a list of pairs to support multiclassing.
    var characterClass: List<ClassLevel> = emptyList(),

    var characterPersonalityTraits: StringListWrapper = StringListWrapper(),
    var characterIdeals: StringListWrapper = StringListWrapper(),
    var characterBonds: StringListWrapper = StringListWrapper(),
    var characterFlaws: StringListWrapper = StringListWrapper(),
    var characterFeaturesAndTraits: StringListWrapper = StringListWrapper(),
    var inspirationPoints: IntWrapper = IntWrapper(value = 0),
    var proficiencyBonus: StatList  = StatList(),
    var armorClass: StatList = StatList(),
    var initiative: StatList  = StatList(),
    var speed: StatList  = StatList(),
    var hitDice: String = "",
    var hitPointCurrent: IntWrapper = IntWrapper(value = 0),
    var hitpointMax: StatList = StatList(),
    var tempHitPoints: IntWrapper = IntWrapper(value = 0),
    var strength: StatList = StatList(),
    var strengthBonus: StatList = StatList(),
    var dexterity: StatList = StatList(),
    var dexterityBonus: StatList = StatList(),
    var constitution: StatList = StatList(),
    var constitutionBonus: StatList = StatList(),
    var intelligence: StatList = StatList(),
    var intelligenceBonus: StatList = StatList(),
    var wisdom: StatList = StatList(),
    var wisdomBonus: StatList = StatList(),
    var charisma: StatList = StatList(),
    var charismaBonus: StatList = StatList(),

    var attacks: List<Attack> = emptyList(),
    var equipment: StringListWrapper = StringListWrapper(),
    var proficenciesAndLanguages: StringListWrapper = StringListWrapper(),

    //Currency
    var copperPieces:IntWrapper = IntWrapper(value = 0),
    var silverPieces:IntWrapper = IntWrapper(value = 0),
    var electrumPieces:IntWrapper = IntWrapper(value = 0),
    var goldPieces:IntWrapper = IntWrapper(value = 0),
    var platinumPieces:IntWrapper = IntWrapper(value = 0),

    //Saving Throws
    var strengthSavingThrow: StatListWithProficiency = StatListWithProficiency(),
    var dexteritySavingThrow: StatListWithProficiency = StatListWithProficiency(),
    var constitutionSavingThrow: StatListWithProficiency = StatListWithProficiency(),
    var intelligenceSavingThrow: StatListWithProficiency = StatListWithProficiency(),
    var wisdomSavingThrow: StatListWithProficiency = StatListWithProficiency(),
    var charismaSavingThrow: StatListWithProficiency = StatListWithProficiency(),

    //Skills
    var acrobatics: StatListWithProficiency = StatListWithProficiency(),
    var animalhandling: StatListWithProficiency = StatListWithProficiency(),
    var arcana: StatListWithProficiency = StatListWithProficiency(),
    var athletics:StatListWithProficiency = StatListWithProficiency(),
    var deception: StatListWithProficiency = StatListWithProficiency(),
    var history: StatListWithProficiency = StatListWithProficiency(),
    var insight: StatListWithProficiency = StatListWithProficiency(),
    var intimidation: StatListWithProficiency = StatListWithProficiency(),
    var investigation: StatListWithProficiency = StatListWithProficiency(),
    var medicine: StatListWithProficiency = StatListWithProficiency(),
    var nature: StatListWithProficiency = StatListWithProficiency(),
    var perception: StatListWithProficiency = StatListWithProficiency(),
    var performance: StatListWithProficiency = StatListWithProficiency(),
    var persuasion: StatListWithProficiency = StatListWithProficiency(),
    var religion: StatListWithProficiency = StatListWithProficiency(),
    var slightOfHand: StatListWithProficiency = StatListWithProficiency(),
    var stealth: StatListWithProficiency = StatListWithProficiency(),
    var survival: StatListWithProficiency = StatListWithProficiency(),
    var passivePerception: StatList = StatList(),

    //Appearance:
    var age:String = "",
    var height: String = "",
    var weight: String = "",
    var eyes: String = "",
    var skin: String = "",
    var hair: String = "",
    //TODO: Add image var

    var alliesAndOrganizations: StringListWrapper = StringListWrapper(),
    var characterBackstory: StringListWrapper = StringListWrapper(),

    var treasure: StringListWrapper = StringListWrapper(),

    var spellCastingClass: CharacterClass? = null,
    var spellCastingAbility: CharacterStat = CharacterStat.INTELLIGENCE,
    var spellSaveDC: StatList = StatList(),
    var spellAttackBonus: StatList = StatList(),

    var spellBook: SpellBook = SpellBook()
)
