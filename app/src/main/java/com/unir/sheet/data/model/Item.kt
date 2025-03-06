package com.unir.sheet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.unir.sheet.data.remote.model.ApiItem

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
    fun toApiItem(): ApiItem {
        return ApiItem(
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

