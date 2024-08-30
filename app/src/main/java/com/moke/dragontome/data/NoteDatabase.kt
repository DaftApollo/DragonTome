package com.moke.dragontome.data

import androidx.room.Database
import androidx.room.Room
import android.content.Context
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = true)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var Instance: NoteDatabase? = null

        fun getDatabase(context: Context):NoteDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context = context, NoteDatabase::class.java, "note_database")
                    .fallbackToDestructiveMigration()
                    .createFromAsset(databaseFilePath = "database/notes.db")
                    .build()
                    .also { Instance = it }
            }
        }

    }

}