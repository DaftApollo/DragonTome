package com.example.dragontome.state

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragontome.data.CharacterSheet
import com.example.dragontome.data.CharacterSheetHolder
import com.example.dragontome.data.JSONSheet
import com.example.dragontome.data.SheetDao
import com.example.dragontome.data.SheetDatabase
import com.example.dragontome.data.Spell
import com.example.dragontome.data.SpellDao
import com.example.dragontome.data.SpellDatabase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
            Log.d("debug", "Current Character: ${characterSheet.toString()}")
            Log.d("debug", "Spell: ${spell.toString()}")

            if (spell.level.equals("Cantrip")) {
                characterSheet.spellBook.cantrips = characterSheet.spellBook.cantrips.plus(spell)
                Log.d("debug", "Should have added a cantrip")
            } else if (spell.level.equals("1")) {
                characterSheet.spellBook.levelOneSpells = characterSheet.spellBook.levelOneSpells.plus(spell)
                Log.d("debug", "Should have added a Level one spell")
            } else if (spell.level.equals("2")) {
                characterSheet.spellBook.levelTwoSpells = characterSheet.spellBook.levelTwoSpells.plus(spell)
                Log.d("debug", "Should have added a Level two spell")
            } else if (spell.level.equals("3")) {
                characterSheet.spellBook.levelThreeSpells = characterSheet.spellBook.levelThreeSpells.plus(spell)
                Log.d("debug", "Should have added a Level three spell")
            } else if (spell.level.equals("4")) {
                characterSheet.spellBook.levelFourSpells = characterSheet.spellBook.levelFourSpells.plus(spell)
                Log.d("debug", "Should have added a Level four spell")
            } else if (spell.level.equals("5")) {
                characterSheet.spellBook.levelFiveSpells = characterSheet.spellBook.levelFiveSpells.plus(spell)
                Log.d("debug", "Should have added a Level five spell")
            } else if (spell.level.equals("6")) {
                characterSheet.spellBook.levelSixSpells = characterSheet.spellBook.levelSixSpells.plus(spell)
                Log.d("debug", "Should have added a Level six spell")
            } else if (spell.level.equals("7")) {
                characterSheet.spellBook.levelSevenSpells = characterSheet.spellBook.levelSevenSpells.plus(spell)
                Log.d("debug", "Should have added a Level seven spell")
            } else if (spell.level.equals("8")) {
                characterSheet.spellBook.levelEightSpells = characterSheet.spellBook.levelEightSpells.plus(spell)
                Log.d("debug", "Should have added a Level eight spell")
            } else if (spell.level.equals("9")) {
                characterSheet.spellBook.levelNineSpells = characterSheet.spellBook.levelNineSpells.plus(spell)
                Log.d("debug", "Should have added a Level nine spell")
            } else{
                Log.d("debug", "Tried adding a spell that didn't match a level.")
            }
            Log.d("debug", "${characterSheet.spellBook.toString()}")
        }
        else if(characterSheet != null && additionMode != true){
            Log.d("debug", "Current Character: ${characterSheet.toString()}")
            Log.d("debug", "Spell: ${spell.toString()}")
            if (spell.level.equals("Cantrip")) {
                characterSheet.spellBook.cantrips = characterSheet.spellBook.cantrips.minus(spell)
                Log.d("debug", "Should have removed a Cantrip spell")
            } else if (spell.level.equals("1")) {
                characterSheet.spellBook.levelOneSpells = characterSheet.spellBook.levelOneSpells.minus(spell)
                Log.d("debug", "Should have removed a Level one spell")
            } else if (spell.level.equals("2")) {
                characterSheet.spellBook.levelTwoSpells = characterSheet.spellBook.levelTwoSpells.minus(spell)
                Log.d("debug", "Should have removed a Level two spell")
            } else if (spell.level.equals("3")) {
                characterSheet.spellBook.levelThreeSpells = characterSheet.spellBook.levelThreeSpells.minus(spell)
                Log.d("debug", "Should have removed a Level three spell")
            } else if (spell.level.equals("4")) {
                characterSheet.spellBook.levelFourSpells = characterSheet.spellBook.levelFourSpells.minus(spell)
                Log.d("debug", "Should have removed a Level four spell")
            } else if (spell.level.equals("5")) {
                characterSheet.spellBook.levelFiveSpells = characterSheet.spellBook.levelFiveSpells.minus(spell)
                Log.d("debug", "Should have removed a Level five spell")
            } else if (spell.level.equals("6")) {
                characterSheet.spellBook.levelSixSpells = characterSheet.spellBook.levelSixSpells.minus(spell)
                Log.d("debug", "Should have removed a Level six spell")
            } else if (spell.level.equals("7")) {
                characterSheet.spellBook.levelSevenSpells = characterSheet.spellBook.levelSevenSpells.minus(spell)
                Log.d("debug", "Should have removed a Level seven spell")
            } else if (spell.level.equals("8")) {
                characterSheet.spellBook.levelEightSpells = characterSheet.spellBook.levelEightSpells.minus(spell)
                Log.d("debug", "Should have removed a Level eight spell")
            } else if (spell.level.equals("9")) {
                characterSheet.spellBook.levelNineSpells = characterSheet.spellBook.levelNineSpells.minus(spell)
                Log.d("debug", "Should have removed a Level nine spell")
            } else{
                Log.d("debug", "Tried removing a spell that didn't match a level.")
            }
            Log.d("debug", "${characterSheet.spellBook.toString()}")
        }
        else{
            Log.d("debug", "Called the spell addition/removal function, current character is null if this is called.")
            Log.d("debug", "Current Character: ${characterSheet.toString()}")
            Log.d("debug", "Spell: ${spell.toString()}")
        }

    }

    fun updateDatabase(characterSheet: CharacterSheet){

        GlobalScope.launch {sheetDao.updateDatabase(Json{prettyPrint = true; encodeDefaults = true}.encodeToString(
            currentCharacter!!.characterSheet), id = currentCharacter!!.id) }
        Log.d("debug", "Should have updated the database.")
        currentCharacter!!.characterSheet = characterSheet
        Log.d("debug", "Current character should have been updated: Current: ${currentCharacter.toString()}")
        sheetList.forEach {
            if (it.id == currentCharacter!!.id){
                it.characterSheet = currentCharacter!!.characterSheet
            }
            Log.d("debug", "Should have updated an entry in the sheetlist. id = ${currentCharacter!!.id}")
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
            Log.d("debug", "Should have created a new character with id ${id}")
            refreshContent()
        }
    }

    fun RemoveFromDatabase(characterSheetHolder: CharacterSheetHolder, refreshContent: () -> Unit = {}){
        GlobalScope.launch {
            sheetDao.delete(JSONSheet(Json{prettyPrint = true; encodeDefaults = true}.encodeToString(characterSheetHolder.characterSheet), characterSheetHolder.id))
            sheetList = populateSheets(sheetDao)
            Log.d("debug", "Should have deleted a character with the id ${characterSheetHolder.id}")
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
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}