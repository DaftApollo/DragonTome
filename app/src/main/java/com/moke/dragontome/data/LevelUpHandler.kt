package com.moke.dragontome.data

import android.util.Log
import com.moke.dragontome.state.AppViewModel

object LevelUpHandler {

    val artificerSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ALCHEMIST,
        CharacterSubClass.ARMORER,
        CharacterSubClass.ARTILLERIST,
        CharacterSubClass.BATTLE_SMITH
    )
    val barbarianSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ANCESTRAL_GUARDIAN,
        CharacterSubClass.BATTLERAGER,
        CharacterSubClass.BEAST,
        CharacterSubClass.BERSERKER,
        CharacterSubClass.GIANT,
        CharacterSubClass.STORM_HERALD,
        CharacterSubClass.TOTEM_WARRIOR,
        CharacterSubClass.WILD_MAGIC_BARBARIAN,
        CharacterSubClass.ZEALOT,
    )
    val bardSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.CREATION,
        CharacterSubClass.ELOQUENCE,
        CharacterSubClass.GLAMOUR,
        CharacterSubClass.SPIRITS,
        CharacterSubClass.LORE,
        CharacterSubClass.SWORDS,
        CharacterSubClass.VALOR,
        CharacterSubClass.WHISPERS
    )
    val bloodHunterSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.GHOSTSLAYER,
        CharacterSubClass.LYCAN,
        CharacterSubClass.MUTANT,
        CharacterSubClass.PROFANE_SOUL
    )
    val clericSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ARCANA,
        CharacterSubClass.DEATH,
        CharacterSubClass.FORGE,
        CharacterSubClass.GRAVE,
        CharacterSubClass.KNOWLEDGE,
        CharacterSubClass.LIFE,
        CharacterSubClass.LIGHT,
        CharacterSubClass.NATURE,
        CharacterSubClass.ORDER,
        CharacterSubClass.PEACE,
        CharacterSubClass.TEMPEST,
        CharacterSubClass.TRICKERY,
        CharacterSubClass.TWILIGHT,
        CharacterSubClass.WAR
    )
    val druidSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.DREAMS,
        CharacterSubClass.LAND,
        CharacterSubClass.MOON,
        CharacterSubClass.SHEPHERD,
        CharacterSubClass.SPORES,
        CharacterSubClass.STARS,
        CharacterSubClass.WILDFIRE
    )
    val fighterSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ARCANE_ARCHER,
        CharacterSubClass.BANNERET,
        CharacterSubClass.BATTLE_MASTER,
        CharacterSubClass.CAVALIER,
        CharacterSubClass.CHAMPION,
        CharacterSubClass.ECHO_KNIGHT,
        CharacterSubClass.ELDRITCH_KNIGHT,
        CharacterSubClass.PSI_WARRIOR,
        CharacterSubClass.RUNE_KNIGHT,
        CharacterSubClass.SAMURAI,
    )
    val monkSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ASTRAL_SELF,
        CharacterSubClass.ASCENDANT_DRAGON,
        CharacterSubClass.DRUNKEN_MASTER,
        CharacterSubClass.FOUR_ELEMENTS,
        CharacterSubClass.KENSEI,
        CharacterSubClass.LONG_DEATH,
        CharacterSubClass.MERCY,
        CharacterSubClass.OPEN_HAND,
        CharacterSubClass.SHADOW,
        CharacterSubClass.SUN_SOUL,
    )
    val paladinSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ANCIENTS,
        CharacterSubClass.CONQUEST,
        CharacterSubClass.CROWN,
        CharacterSubClass.DEVOTION,
        CharacterSubClass.GLORY,
        CharacterSubClass.REDEMPTION,
        CharacterSubClass.VENGEANCE,
        CharacterSubClass.WATCHERS,
        CharacterSubClass.OATHBREAKER
    )
    val rangerSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.BEAST_MASTER,
        CharacterSubClass.FEY_WANDERER,
        CharacterSubClass.GLOOM_STALKER,
        CharacterSubClass.HORIZON_WALKER,
        CharacterSubClass.HUNTER,
        CharacterSubClass.MONSTER_SLAYER,
        CharacterSubClass.SWARMKEEPER,
        CharacterSubClass.DRAKEWARDEN
    )
    val rogueSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ARCANE_TRICKSTER,
        CharacterSubClass.ASSASSIN,
        CharacterSubClass.INQUSITIVE,
        CharacterSubClass.MASTERMIND,
        CharacterSubClass.PHANTOM,
        CharacterSubClass.SCOUT,
        CharacterSubClass.SOULKNIFE,
        CharacterSubClass.SWASHBUCKLER,
        CharacterSubClass.THIEF,
    )
    val sorcererSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ABERRANT_MIND,
        CharacterSubClass.CLOCKWORD_SOUL,
        CharacterSubClass.DRACONIC_BLOODLINE,
        CharacterSubClass.DIVINE_SOUL,
        CharacterSubClass.LUNAR_SORCERY,
        CharacterSubClass.SHADOW_MAGIC,
        CharacterSubClass.STORM_SORCERY,
        CharacterSubClass.WILD_MAGIC_SORCERER
    )
    val warlockSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ARCHFEY,
        CharacterSubClass.CELESTIAL,
        CharacterSubClass.FATHOMLESS,
        CharacterSubClass.FIEND,
        CharacterSubClass.GENIE,
        CharacterSubClass.GREAT_OLD_ONE,
        CharacterSubClass.HEXBLADE,
        CharacterSubClass.UNDEAD,
        CharacterSubClass.UNDYING,
    )
    val wizardSubclasses:List<CharacterSubClass> = listOf(
        CharacterSubClass.ABJURATION,
        CharacterSubClass.BLADESINGING,
        CharacterSubClass.CHRONURGY,
        CharacterSubClass.CONJURATION,
        CharacterSubClass.DIVINATION,
        CharacterSubClass.ENCHANTMENT,
        CharacterSubClass.EVOCATION,
        CharacterSubClass.GRAVITURGY,
        CharacterSubClass.ILLUSION,
        CharacterSubClass.NECROMANCY,
        CharacterSubClass.ORDER_OF_SCRIBES,
        CharacterSubClass.TRANSMUTATION,
        CharacterSubClass.WAR_MAGIC
    )


   private var modifiedState:List<ClassLevel> = emptyList()
    fun setModifierState(list:List<ClassLevel>){
        modifiedState = list
        Log.d("debug", "Updated modified state")
        Log.d("debug", "${modifiedState.toString()}")
    }

    fun updateCharacterFeatures(appViewModel: AppViewModel){
        //Wipe all generated features from the list
        appViewModel.currentCharacter!!.characterSheet.characterFeaturesAndTraits.stringList.forEach {
            if(it.isGenerated){
                appViewModel.currentCharacter!!.characterSheet.characterFeaturesAndTraits.stringList = appViewModel.currentCharacter!!.characterSheet.characterFeaturesAndTraits.stringList.minus(it)
            }
        }
        //Add all the relevant features for detected classes and levels
        appViewModel.currentCharacter!!.characterSheet.characterClass.forEach { classLevel ->
            if (classLevel.characterClass != CharacterClass.CUSTOM){
                for (level in 1..classLevel.classLevel){
                    if(level > 20)
                        break

                    val classText = classLevel.characterClass.toString() + level.toString()


                    Log.d("debug","Would have grabbed entry $classText")
                    if(classLevel.characterSubClass != CharacterSubClass.CUSTOM){

                        val subclassText = classLevel.characterSubClass.toString() + level.toString()

                        Log.d("debug", "Would have grabbed entry $subclassText")
                    }
                }
            }
        }
    }
}