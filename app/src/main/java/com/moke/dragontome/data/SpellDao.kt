package com.moke.dragontome.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SpellDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(spell: Spell)

    @Update
    suspend fun update(spell: Spell)

    @Delete
    suspend fun delete(spell: Spell)

    @Query("SELECT * from spells WHERE id = :id")
    fun getSpell(id: Int): Spell

    @Query("SELECT * from spells ORDER BY id ASC")
    suspend fun getAllSpells(): List<Spell>
}