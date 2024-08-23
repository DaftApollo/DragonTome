package com.moke.dragontome.data


//Repository that provides insert, update, delete, and retrieval of [Spell] from a given data source
interface SpellsRepository {
    suspend fun getAllSpellsStream(): List<Spell>

    fun getSpellStream(id: Int): Spell?

    suspend fun insertSpell(spell: Spell)

    suspend fun deleteSpell(spell: Spell)

    suspend fun updateSpell(spell: Spell)

}