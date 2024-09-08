package com.moke.dragontome.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
* I don't particularly understand a lot of what's going on with the creation of the database here,
* but I got the implementation online and it seems to work well for all of the databases I required,
* whether they were pre-built or not.
* */
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