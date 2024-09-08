package com.moke.dragontome.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FeatureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(feature: Feature)

    @Update
    suspend fun update(feature: Feature)

    @Delete
    suspend fun delete(feature: Feature)

    @Query("SELECT * from features")
    suspend fun getAllFeatures():List<Feature>
}