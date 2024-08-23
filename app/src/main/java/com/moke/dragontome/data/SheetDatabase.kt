package com.moke.dragontome.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [JSONSheet::class], version = 1, exportSchema = true)
abstract class SheetDatabase : RoomDatabase() {
    abstract fun sheetDao(): SheetDao

    companion object{
        @Volatile
        private var Instance: SheetDatabase? = null

        fun getDatabase(context: Context): SheetDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context = context, SheetDatabase::class.java, "sheet_database")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/sheets.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}