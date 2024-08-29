package com.moke.dragontome.data

import androidx.core.util.Predicate

object SpellFilterObject {
    //Spell Filter object to remember state of the filter
    var cantripSelected = false
    var oneSelected = false
    var twoSelected = false
    var threeSelected = false
    var fourSelected = false
    var fiveSelected = false
    var sixSelected = false
    var sevenSelected = false
    var eightSelected = false
    var nineSelected = false

    var abjurationSelected = false
    var conjurationSelected = false
    var divinationSelected = false
    var enchantmentSelected = false
    var evocationSelected = false
    var illusionSelected = false
    var necromancySelected = false
    var transmutationSelected = false

    var titleSearch:String = ""
    var descriptionSearch: String = ""

    fun clearFilter(){
        cantripSelected = false
        oneSelected = false
        twoSelected = false
        threeSelected = false
        fourSelected = false
        fiveSelected = false
        sixSelected = false
        sevenSelected = false
        eightSelected = false
        nineSelected = false

        abjurationSelected = false
        conjurationSelected = false
        divinationSelected = false
        enchantmentSelected = false
        evocationSelected = false
        illusionSelected = false
        necromancySelected = false
        transmutationSelected = false

        titleSearch = ""
        descriptionSearch = ""
    }

    fun isLevelFiltering():Boolean{
        return (cantripSelected || oneSelected || twoSelected || threeSelected || fourSelected || fiveSelected || sixSelected || sevenSelected || eightSelected || nineSelected)
    }

    fun isSchoolFiltering():Boolean{
        return(abjurationSelected ||
                conjurationSelected ||
                divinationSelected ||
                enchantmentSelected ||
                evocationSelected ||
                illusionSelected ||
                necromancySelected ||
                transmutationSelected)
    }

    fun getFilterPredicate(spell:Spell):Boolean{
        var filteredLevels:String = ""
        if (cantripSelected)
            filteredLevels += "0"
        if (oneSelected)
            filteredLevels += "1"
        if (twoSelected)
            filteredLevels += "2"
        if (threeSelected)
            filteredLevels += "3"
        if (fourSelected)
            filteredLevels += "4"
        if (fiveSelected)
            filteredLevels += "5"
        if (sixSelected)
            filteredLevels += "6"
        if (sevenSelected)
            filteredLevels += "7"
        if (eightSelected)
            filteredLevels += "8"
        if (nineSelected)
            filteredLevels += "9"

        var filteredSchools:String = ""
        if (abjurationSelected)
            filteredSchools += "Abjuration"
        if (divinationSelected)
            filteredSchools += "Divination"
        if (conjurationSelected)
            filteredSchools += "Conjuration"
        if (enchantmentSelected)
            filteredSchools += "Enchantment"
        if (evocationSelected)
            filteredSchools += "Evocation"
        if (illusionSelected)
            filteredSchools += "Illusion"
        if (necromancySelected)
            filteredSchools += "Necromancy"
        if (transmutationSelected)
            filteredSchools += "Transmutation"

        val levelPredicate = if (isLevelFiltering()) Predicate<Spell> {filteredLevels.contains(it.level)} else Predicate<Spell> { true }
        val schoolPredicate = if(isSchoolFiltering()) Predicate<Spell> { filteredSchools.contains(it.school) } else Predicate<Spell> { true }
        val titlePredicate = Predicate<Spell> { it.name.contains(titleSearch, ignoreCase = true) }
        val descriptionPredicate = Predicate<Spell> { it.text.contains(descriptionSearch, ignoreCase = true) }
        return levelPredicate.and(schoolPredicate).and(titlePredicate).and(descriptionPredicate).test(spell)
    }

}