package com.moke.dragontome.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Feature::class], version = 1, exportSchema = true)
abstract class FeatureDatabase : RoomDatabase() {
    abstract fun featureDao(): FeatureDao

    companion object{
        @Volatile
        private var Instance: FeatureDatabase? = null

        fun getDatabase(context: Context):FeatureDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context = context, FeatureDatabase::class.java, "feature_database")
                    .fallbackToDestructiveMigration()
                    .createFromAsset(databaseFilePath = "database/features.db")
                    .build()
                    .also { Instance = it}
            }
        }
    }
}