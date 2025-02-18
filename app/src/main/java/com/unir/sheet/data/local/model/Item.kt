package com.unir.sheet.data.local.model

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

    @ColumnInfo(name = "damageDice")
    val damageDice: String,

    @ColumnInfo(name = "damageType")
    val damageType: String,

    @ColumnInfo(name = "goldValue")
    val goldValue: Int = 0,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "rangedAttackPossible")
    val rangedAttackPossible: Boolean,

    @ColumnInfo(name = "range")
    val range: Float,

    @ColumnInfo(name = "document")
    val document: String,

    )

