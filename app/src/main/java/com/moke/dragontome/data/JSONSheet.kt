package com.moke.dragontome.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sheets")
data class JSONSheet(
    @ColumnInfo(name = "data") var data:String,

    @PrimaryKey(autoGenerate = true) val id:Int
)
