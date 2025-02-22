package com.unir.sheet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "itemTable",
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("characterId")]
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "characterId")
    val characterId: Int?,

    @ColumnInfo(name = "id_game_session")
    val gameSession: Int?,


    // DEMÁS ATRIBUTOS
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "imgUrl")
    val imgUrl: String,

    @ColumnInfo(name = "goldValue")
    val goldValue: Int = 0,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "dice")
    val dice: Int,

    @ColumnInfo(name = "statType")
    val statType: String,

    @ColumnInfo(name = "statValue")
    val statValue: Int,

    @ColumnInfo(name = "quantity")
    val quantity: Int,


    )

