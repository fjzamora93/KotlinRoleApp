package com.roleapp.character.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.roleapp.character.data.model.remote.ItemDTO
import com.unir.roleapp.character.data.model.local.ItemCategory

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "id_game_session") var gameSession: Int?,

    // DEMÁS ATRIBUTOS
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "imgUrl") val imgUrl: String,
    @ColumnInfo(name = "goldValue") val goldValue: Int = 0,
    @ColumnInfo(name = "category") val category: ItemCategory,
    @ColumnInfo(name = "dice") val dice: Int,
    @ColumnInfo(name = "diceAmount") val diceAmount: Int = 1,
    @ColumnInfo(name = "statType") val statType: String,
    @ColumnInfo(name = "statValue") val statValue: Int,

    ){


    fun toApiItem(): ItemDTO {
        return ItemDTO(
            id = this.id,
            gameSession = this.gameSession,
            name = this.name,
            description = this.description,
            imgUrl = this.imgUrl,
            goldValue = this.goldValue,
            category = ItemCategory.getString(this.category),
            dice = this.dice,
            diceAmount = this.diceAmount,
            statValue = this.statValue,
            statType = this.statType
        )
    }
}

