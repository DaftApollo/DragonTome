package com.moke.dragontome.state

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moke.dragontome.data.CharacterSheet
import com.moke.dragontome.data.CharacterSheetHolder
import com.moke.dragontome.data.JSONSheet
import com.moke.dragontome.data.SheetDao
import com.moke.dragontome.data.SheetDatabase
import com.moke.dragontome.data.Spell
import com.moke.dragontome.data.SpellDao
import com.moke.dragontome.data.SpellDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AppViewModel(context: Context) : ViewModel() {

    var currentCharacter:CharacterSheetHolder? = null
    private val spellDB = SpellDatabase.getDatabase(context = context)
    val spellDao = spellDB.spellDao()
    var spellList: List<Spell> = emptyList()

    private val sheetDB = SheetDatabase.getDatabase(context = context)
    val sheetDao = sheetDB.sheetDao()
    var sheetList: List<CharacterSheetHolder> = emptyList()

    init {
        viewModelScope.launch{
            spellList = populateSpells(spellDao)
            sheetList = populateSheets(sheetDao)
        }
    }

    suspend fun populateSpells(spellDao: SpellDao) :List<Spell> {
        var spellList: List<Spell> = emptyList()

        spellList = spellDao.getAllSpells()

        return spellList
    }

    suspend fun populateSheets(sheetDao: SheetDao) : List<CharacterSheetHolder>{
        var sheetList: List<JSONSheet> = emptyList()
        var characterSheetList: List<CharacterSheetHolder> = emptyList()
        sheetList = sheetDao.getAllSheets()
        sheetList.forEach {
            it.data = it.data.replace("'", "")

            val characterSheet = Json.decodeFromString<CharacterSheet>(it.data)
            characterSheetList = characterSheetList.plus(CharacterSheetHolder(characterSheet = characterSheet, id = it.id))
        }
        return characterSheetList
    }

    fun addSpell(additionMode: Boolean, spell: Spell, characterSheet: CharacterSheet? =  if (currentCharacter != null) currentCharacter!!.characterSheet else null){
        if(characterSheet != null && additionMode == true){
            if (spell.level.equals("Cantrip")) {
                characterSheet.spellBook.cantrips = characterSheet.spellBook.cantrips.plus(spell)
            } else if (spell.level.equals("1")) {
                characterSheet.spellBook.levelOneSpells = characterSheet.spellBook.levelOneSpells.plus(spell)
            } else if (spell.level.equals("2")) {
                characterSheet.spellBook.levelTwoSpells = characterSheet.spellBook.levelTwoSpells.plus(spell)
            } else if (spell.level.equals("3")) {
                characterSheet.spellBook.levelThreeSpells = characterSheet.spellBook.levelThreeSpells.plus(spell)
            } else if (spell.level.equals("4")) {
                characterSheet.spellBook.levelFourSpells = characterSheet.spellBook.levelFourSpells.plus(spell)
            } else if (spell.level.equals("5")) {
                characterSheet.spellBook.levelFiveSpells = characterSheet.spellBook.levelFiveSpells.plus(spell)
            } else if (spell.level.equals("6")) {
                characterSheet.spellBook.levelSixSpells = characterSheet.spellBook.levelSixSpells.plus(spell)
            } else if (spell.level.equals("7")) {
                characterSheet.spellBook.levelSevenSpells = characterSheet.spellBook.levelSevenSpells.plus(spell)
            } else if (spell.level.equals("8")) {
                characterSheet.spellBook.levelEightSpells = characterSheet.spellBook.levelEightSpells.plus(spell)
            } else if (spell.level.equals("9")) {
                characterSheet.spellBook.levelNineSpells = characterSheet.spellBook.levelNineSpells.plus(spell)
            } else{
            }
        }
        else if(characterSheet != null && additionMode != true){

            if (spell.level.equals("Cantrip")) {
                characterSheet.spellBook.cantrips = characterSheet.spellBook.cantrips.minus(spell)
            } else if (spell.level.equals("1")) {
                characterSheet.spellBook.levelOneSpells = characterSheet.spellBook.levelOneSpells.minus(spell)

            } else if (spell.level.equals("2")) {
                characterSheet.spellBook.levelTwoSpells = characterSheet.spellBook.levelTwoSpells.minus(spell)
            } else if (spell.level.equals("3")) {
                characterSheet.spellBook.levelThreeSpells = characterSheet.spellBook.levelThreeSpells.minus(spell)
            } else if (spell.level.equals("4")) {
                characterSheet.spellBook.levelFourSpells = characterSheet.spellBook.levelFourSpells.minus(spell)
            } else if (spell.level.equals("5")) {
                characterSheet.spellBook.levelFiveSpells = characterSheet.spellBook.levelFiveSpells.minus(spell)
            } else if (spell.level.equals("6")) {
                characterSheet.spellBook.levelSixSpells = characterSheet.spellBook.levelSixSpells.minus(spell)
            } else if (spell.level.equals("7")) {
                characterSheet.spellBook.levelSevenSpells = characterSheet.spellBook.levelSevenSpells.minus(spell)
            } else if (spell.level.equals("8")) {
                characterSheet.spellBook.levelEightSpells = characterSheet.spellBook.levelEightSpells.minus(spell)
            } else if (spell.level.equals("9")) {
                characterSheet.spellBook.levelNineSpells = characterSheet.spellBook.levelNineSpells.minus(spell)
            } else{
            }
        }
        else{
        }

    }

    fun updateDatabase(characterSheet: CharacterSheet){

        GlobalScope.launch {sheetDao.updateDatabase(Json{prettyPrint = true; encodeDefaults = true}.encodeToString(
            currentCharacter!!.characterSheet), id = currentCharacter!!.id) }
        currentCharacter!!.characterSheet = characterSheet
        sheetList.forEach {
            if (it.id == currentCharacter!!.id){
                it.characterSheet = currentCharacter!!.characterSheet
            }
        }

    }

    fun AddToDatabase(characterSheet: CharacterSheet, refreshContent: () -> Unit = {}){
        var id:Int = 1
        //Find the correct id for a new insertion
        sheetList.forEach {
            if (it.id >= id){
                id = it.id + 1
            }
        }
        GlobalScope.launch {
            sheetDao.insert(JSONSheet(Json{prettyPrint = true; encodeDefaults = true}.encodeToString(characterSheet), id))
            //Refresh the sheetlist with the sheets from the database
            sheetList = populateSheets(sheetDao)
            refreshContent()
        }
    }

    fun RemoveFromDatabase(characterSheetHolder: CharacterSheetHolder, refreshContent: () -> Unit = {}){
        GlobalScope.launch {
            sheetDao.delete(JSONSheet(Json{prettyPrint = true; encodeDefaults = true}.encodeToString(characterSheetHolder.characterSheet), characterSheetHolder.id))
            sheetList = populateSheets(sheetDao)
            refreshContent()
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

}