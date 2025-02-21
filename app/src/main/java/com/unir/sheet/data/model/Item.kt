package com.unir.sheet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "itemTable")
data class Item(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "id")
    val id: String,

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


    )

