package com.moke.dragontome.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * from notes")
    suspend fun getAllNotes():List<Note>

    @Query("UPDATE notes SET title = :title WHERE id = :id")
    suspend fun updateNoteTitle(title:String, id: Int)

    @Query("UPDATE notes SET data = :data WHERE id = :id")
    suspend fun updateNoteData(data:String, id: Int)
}