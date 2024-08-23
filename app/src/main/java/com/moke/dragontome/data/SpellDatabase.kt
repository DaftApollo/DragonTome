package com.moke.dragontome.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Spell::class], version = 1, exportSchema = true)
abstract class SpellDatabase : RoomDatabase() {
    abstract fun spellDao(): SpellDao

    companion object {
        @Volatile
        private var Instance: SpellDatabase? = null

        fun getDatabase(context: Context): SpellDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SpellDatabase::class.java, "spell_database")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/spells.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}