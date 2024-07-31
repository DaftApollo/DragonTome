package com.example.dragontome.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SheetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(jsonSheet: JSONSheet)

    @Update
    suspend fun update(jsonSheet: JSONSheet)

    @Delete
    suspend fun delete(jsonSheet: JSONSheet)

    @Query("SELECT * from sheets WHERE id = :id")
    fun getSheet(id: Int): JSONSheet

    @Query("SELECT * from sheets")
    suspend fun getAllSheets(): List<JSONSheet>

    @Query("UPDATE sheets SET data = :data WHERE id = :id")
    suspend fun updateDatabase(data:String, id:Int)

}