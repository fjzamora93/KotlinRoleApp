package com.unir.sheet.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CharacterWithItems(
    @Embedded val character: CharacterEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CharacterItemCrossRef::class)
    )
    val items: List<Item>
)

