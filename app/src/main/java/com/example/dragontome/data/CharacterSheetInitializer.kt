package com.example.dragontome.data

import java.math.MathContext
import java.math.RoundingMode

class CharacterSheetInitializer (var characterSheet: CharacterSheet) {
    fun initializeCharacterSheet(){

        refreshProficiencyBonus()
        characterSheet.armorClass.baseValue = 10
        characterSheet.speed.usesBaseStat = false
        characterSheet.strength.usesBaseStat = false
        characterSheet.dexterity.usesBaseStat = false
        characterSheet.constitution.usesBaseStat = false
        characterSheet.intelligence.usesBaseStat = false
        characterSheet.wisdom.usesBaseStat = false
        characterSheet.charisma.usesBaseStat = false
        refreshStrength()
        refreshDexterity()
        refreshConstitution()
        refreshIntelligence()
        refreshWisdom()
        refreshCharisma()
        refreshHitPointMax()
        refreshInitiative()
        refreshStrengthSave()
        refreshDexteritySave()
        refreshConstitutionSave()
        refreshIntelligenceSave()
        refreshWisdomSave()
        refreshCharismaSave()

        refreshAcrobatics()
        refreshAnimalHandling()
        refreshArcana()
        refreshAthletics()
        refreshDeception()
        refreshHistory()
        refreshInsight()
        refreshIntimidation()
        refreshInvestigation()
        refreshMedicine()
        refreshNature()
        refreshPerception()
        refreshPerformance()
        refreshPersuasion()
        refreshReligion()
        refreshSlightOfHand()
        refreshStealth()
        refreshSurvival()
        refreshPassivePerception()

        refreshSpellSaveDC()
        refreshSpellAttackBonus()

}

    fun refreshProficiencyBonus(){
        characterSheet.proficiencyBonus.baseValue = (((characterSheet.characterClass.sumOf {it.classLevel} - 1) / 4.0f)).toBigDecimal().setScale(1,RoundingMode.FLOOR).toInt() + 2
    }

    fun refreshStrength(){
        characterSheet.strengthBonus.baseValue = if (characterSheet.strength.sumEntries() >= 10) ((characterSheet.strength.sumEntries() - 10) / 2.0f).toBigDecimal().setScale(1, RoundingMode.FLOOR).toInt() else
            ((characterSheet.strength.sumEntries() - 11) / 2.0f).toBigDecimal().setScale(1, RoundingMode.CEILING).toInt()
    }
    fun refreshDexterity(){
        characterSheet.dexterityBonus.baseValue = if (characterSheet.dexterity.sumEntries() >= 10) ((characterSheet.dexterity.sumEntries() - 10) / 2.0f).toBigDecimal().setScale(1, RoundingMode.FLOOR).toInt() else
            ((characterSheet.dexterity.sumEntries() - 11) / 2.0f).toBigDecimal().setScale(1, RoundingMode.CEILING).toInt()
    }
    fun refreshConstitution(){
        characterSheet.constitutionBonus.baseValue = if (characterSheet.constitution.sumEntries() >= 10) ((characterSheet.constitution.sumEntries() - 10) / 2.0f).toBigDecimal().setScale(1, RoundingMode.FLOOR).toInt() else
            ((characterSheet.constitution.sumEntries() - 11) / 2.0f).toBigDecimal().setScale(1, RoundingMode.CEILING).toInt()
    }
    fun refreshIntelligence(){
        characterSheet.intelligenceBonus.baseValue = if (characterSheet.intelligence.sumEntries() >= 10) ((characterSheet.intelligence.sumEntries() - 10) / 2.0f).toBigDecimal().setScale(1, RoundingMode.FLOOR).toInt() else
            ((characterSheet.intelligence.sumEntries() - 11) / 2.0f).toBigDecimal().setScale(1, RoundingMode.CEILING).toInt()
    }
    fun refreshWisdom(){
        characterSheet.wisdomBonus.baseValue = if (characterSheet.wisdom.sumEntries() >= 10) ((characterSheet.wisdom.sumEntries() - 10) / 2.0f).toBigDecimal().setScale(1, RoundingMode.FLOOR).toInt() else
            ((characterSheet.wisdom.sumEntries() - 11) / 2.0f).toBigDecimal().setScale(1, RoundingMode.CEILING).toInt()
    }
    fun refreshCharisma(){
        characterSheet.charismaBonus.baseValue = if (characterSheet.charisma.sumEntries() >= 10) ((characterSheet.charisma.sumEntries() - 10) / 2.0f).toBigDecimal().setScale(1, RoundingMode.FLOOR).toInt() else
            ((characterSheet.charisma.sumEntries() - 11) / 2.0f).toBigDecimal().setScale(1, RoundingMode.CEILING).toInt()
    }
    fun refreshHitPointMax(){
        characterSheet.hitpointMax.baseValue = characterSheet.constitutionBonus.sumEntries() * characterSheet.characterClass.sumOf { it.classLevel }
    }
    fun refreshInitiative(){
        characterSheet.initiative.baseValue = characterSheet.dexterityBonus.sumEntries()
    }
    fun refreshStrengthSave(){
        characterSheet.strengthSavingThrow.statList.baseValue = characterSheet.strengthBonus.sumEntries()
        if(characterSheet.strengthSavingThrow.proficiency)
            characterSheet.strengthSavingThrow.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshDexteritySave(){
        characterSheet.dexteritySavingThrow.statList.baseValue = characterSheet.dexterityBonus.sumEntries()
        if(characterSheet.dexteritySavingThrow.proficiency)
            characterSheet.dexteritySavingThrow.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshConstitutionSave(){
        characterSheet.constitutionSavingThrow.statList.baseValue = characterSheet.constitutionBonus.sumEntries()
        if(characterSheet.constitutionSavingThrow.proficiency)
            characterSheet.constitutionSavingThrow.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshIntelligenceSave(){
        characterSheet.intelligenceSavingThrow.statList.baseValue = characterSheet.intelligenceBonus.sumEntries()
        if(characterSheet.intelligenceSavingThrow.proficiency)
            characterSheet.intelligenceSavingThrow.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshWisdomSave(){
        characterSheet.wisdomSavingThrow.statList.baseValue = characterSheet.wisdomBonus.sumEntries()
        if(characterSheet.wisdomSavingThrow.proficiency)
            characterSheet.wisdomSavingThrow.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshCharismaSave(){
        characterSheet.charismaSavingThrow.statList.baseValue = characterSheet.charismaBonus.sumEntries()
        if(characterSheet.charismaSavingThrow.proficiency)
            characterSheet.charismaSavingThrow.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshAcrobatics(){
        characterSheet.acrobatics.statList.baseValue = characterSheet.dexterityBonus.sumEntries()
        if(characterSheet.acrobatics.proficiency)
            characterSheet.acrobatics.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshAnimalHandling(){
        characterSheet.animalhandling.statList.baseValue = characterSheet.wisdomBonus.sumEntries()
        if(characterSheet.animalhandling.proficiency)
            characterSheet.animalhandling.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshArcana(){
        characterSheet.arcana.statList.baseValue = characterSheet.intelligenceBonus.sumEntries()
        if(characterSheet.arcana.proficiency)
            characterSheet.arcana.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshAthletics(){
        characterSheet.athletics.statList.baseValue = characterSheet.strengthBonus.sumEntries()
        if(characterSheet.athletics.proficiency)
            characterSheet.athletics.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshDeception(){
        characterSheet.deception.statList.baseValue = characterSheet.charismaBonus.sumEntries()
        if(characterSheet.deception.proficiency)
            characterSheet.deception.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshHistory(){
        characterSheet.history.statList.baseValue = characterSheet.intelligenceBonus.sumEntries()
        if(characterSheet.history.proficiency)
            characterSheet.history.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshInsight(){
        characterSheet.insight.statList.baseValue = characterSheet.wisdomBonus.sumEntries()
        if(characterSheet.insight.proficiency)
            characterSheet.insight.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshIntimidation(){
        characterSheet.intimidation.statList.baseValue = characterSheet.charismaBonus.sumEntries()
        if(characterSheet.intimidation.proficiency)
            characterSheet.intimidation.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshInvestigation(){
        characterSheet.investigation.statList.baseValue = characterSheet.intelligenceBonus.sumEntries()
        if(characterSheet.investigation.proficiency)
            characterSheet.investigation.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshMedicine(){
        characterSheet.medicine.statList.baseValue = characterSheet.wisdomBonus.sumEntries()
        if(characterSheet.medicine.proficiency)
            characterSheet.medicine.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshNature(){
        characterSheet.nature.statList.baseValue = characterSheet.intelligenceBonus.sumEntries()
        if(characterSheet.nature.proficiency)
            characterSheet.nature.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshPerception(){
        characterSheet.perception.statList.baseValue = characterSheet.wisdomBonus.sumEntries()
        if(characterSheet.perception.proficiency)
            characterSheet.perception.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshPerformance(){
        characterSheet.performance.statList.baseValue = characterSheet.charismaBonus.sumEntries()
        if(characterSheet.performance.proficiency)
            characterSheet.performance.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshPersuasion(){
        characterSheet.persuasion.statList.baseValue = characterSheet.charismaBonus.sumEntries()
        if(characterSheet.persuasion.proficiency)
            characterSheet.persuasion.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshReligion(){
        characterSheet.religion.statList.baseValue = characterSheet.intelligenceBonus.sumEntries()
        if(characterSheet.religion.proficiency)
            characterSheet.religion.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshSlightOfHand(){
        characterSheet.slightOfHand.statList.baseValue = characterSheet.dexterityBonus.sumEntries()
        if(characterSheet.slightOfHand.proficiency)
            characterSheet.slightOfHand.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshStealth(){
        characterSheet.stealth.statList.baseValue = characterSheet.dexterityBonus.sumEntries()
        if(characterSheet.stealth.proficiency)
            characterSheet.stealth.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshSurvival(){
        characterSheet.survival.statList.baseValue = characterSheet.wisdomBonus.sumEntries()
        if(characterSheet.survival.proficiency)
            characterSheet.survival.statList.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshPassivePerception(){
        characterSheet.passivePerception.baseValue = 10 + characterSheet.wisdomBonus.sumEntries()
        if(characterSheet.perception.proficiency)
            characterSheet.passivePerception.baseValue += characterSheet.proficiencyBonus.sumEntries()
    }
    fun refreshSpellSaveDC(){
        if (characterSheet.spellCastingAbility.equals(CharacterStat.STRENGTH))
                    characterSheet.spellSaveDC.baseValue = 8 + characterSheet.proficiencyBonus.sumEntries() + characterSheet.strengthBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.DEXTERITY))
            characterSheet.spellSaveDC.baseValue = 8 + characterSheet.proficiencyBonus.sumEntries() + characterSheet.dexterityBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.CONSTITUTION))
            characterSheet.spellSaveDC.baseValue = 8 + characterSheet.proficiencyBonus.sumEntries() + characterSheet.constitutionBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.INTELLIGENCE))
            characterSheet.spellSaveDC.baseValue = 8 + characterSheet.proficiencyBonus.sumEntries() + characterSheet.intelligenceBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.WISDOM))
            characterSheet.spellSaveDC.baseValue = 8 + characterSheet.proficiencyBonus.sumEntries() + characterSheet.wisdomBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.CHARISMA))
            characterSheet.spellSaveDC.baseValue = 8 + characterSheet.proficiencyBonus.sumEntries() + characterSheet.charismaBonus.sumEntries()
    }
    fun refreshSpellAttackBonus(){
        if (characterSheet.spellCastingAbility.equals(CharacterStat.STRENGTH))
            characterSheet.spellAttackBonus.baseValue = characterSheet.proficiencyBonus.sumEntries() + characterSheet.strengthBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.DEXTERITY))
            characterSheet.spellAttackBonus.baseValue = characterSheet.proficiencyBonus.sumEntries() + characterSheet.dexterityBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.CONSTITUTION))
            characterSheet.spellAttackBonus.baseValue = characterSheet.proficiencyBonus.sumEntries() + characterSheet.constitutionBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.INTELLIGENCE))
            characterSheet.spellAttackBonus.baseValue = characterSheet.proficiencyBonus.sumEntries() + characterSheet.intelligenceBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.WISDOM))
            characterSheet.spellAttackBonus.baseValue = characterSheet.proficiencyBonus.sumEntries() + characterSheet.wisdomBonus.sumEntries()
        else if (characterSheet.spellCastingAbility.equals(CharacterStat.CHARISMA))
            characterSheet.spellAttackBonus.baseValue = characterSheet.proficiencyBonus.sumEntries() + characterSheet.charismaBonus.sumEntries()
    }
}