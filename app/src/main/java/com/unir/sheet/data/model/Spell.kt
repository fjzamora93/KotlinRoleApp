package com.unir.sheet.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "spellTable")
data class Spell(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    var name: String = "",
    var description: String = "",
    var dice: Int = 0,
    var level: Int = 0,
    var cost: Int = 0,
    var imgUrl: String = "",
    )
