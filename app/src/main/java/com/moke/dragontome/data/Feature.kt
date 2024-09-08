package com.moke.dragontome.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "features")
data class Feature(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "title") var title:String = "",

    @ColumnInfo(name = "data") var data: String = ""
)
