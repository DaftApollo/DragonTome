package com.example.dragontome.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "spells")
data class Spell(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    //Spell name

    @ColumnInfo(name = "name") val name: String = "",

    //Spell Level
    @ColumnInfo(name = "level") val level: String = "",

    //Spell School
    @ColumnInfo(name = "school") val school: String = "",

    //Spell Casting Time
    @ColumnInfo(name = "castTime") val castTime: String = "",

    //Spell Range
    @ColumnInfo(name = "range") val range: String = "",

    //Spell Components
    @ColumnInfo(name = "components") val components: String = "",

    //Spell Duration
    @ColumnInfo(name = "duration") val duration: String = "",

    //TODO: Add available classes

    //Spell Source
    @ColumnInfo(name = "source") val source: String = "",

    //Spell Text
    @ColumnInfo(name = "description") val text: String = ""

)
