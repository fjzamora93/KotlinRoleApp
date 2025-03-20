package com.unir.character.data.model.local


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "spellTable")
data class Spell(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "characterId")
    var characterId: Long = 0,

    var name: String = "",
    var description: String = "",
    var dice: Int = 0,

    @ColumnInfo(name = "diceAmount") val diceAmount: Int = 1,

    var level: Int = 0,
    var cost: Int = 0,
    var imgUrl: String = "",
    )
