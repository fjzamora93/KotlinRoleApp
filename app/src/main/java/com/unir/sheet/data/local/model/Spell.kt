package com.unir.sheet.data.local.model


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

    var desc: String = "",
    var level: Int = 0,
    var targetCount: Int = 0,
    var range: Double = 0.0,

    )
