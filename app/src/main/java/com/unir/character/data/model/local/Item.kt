package com.unir.character.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unir.character.data.model.remote.ItemDTO

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "id_game_session")  val gameSession: Int?,

    // DEMÁS ATRIBUTOS
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "imgUrl") val imgUrl: String,
    @ColumnInfo(name = "goldValue") val goldValue: Int = 0,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "dice") val dice: Int,
    @ColumnInfo(name = "statType") val statType: String,
    @ColumnInfo(name = "statValue") val statValue: Int,

    ){
    fun toApiItem(): ItemDTO {
        return ItemDTO(
            id = this.id ?: 0,
            gameSession = this.gameSession,
            name = this.name,
            description = this.description,
            imgUrl = this.imgUrl ?: "",
            goldValue = this.goldValue,
            category = this.category,
            dice = this.dice,
            statValue = this.statValue,
            statType = this.statType
        )
    }
}

