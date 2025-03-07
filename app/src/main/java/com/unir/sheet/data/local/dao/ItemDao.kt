package com.unir.sheet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unir.sheet.data.model.CharacterItemCrossRef
import com.unir.sheet.data.model.Item

@Dao
interface ItemDao {


    @Transaction
    @Query("SELECT * FROM item_table WHERE id IN (SELECT itemId FROM character_item_cross_ref WHERE characterId = :characterId)")
    suspend fun getItemsByCharacterId(characterId: Int): List<Item>

    @Query("SELECT * FROM character_item_cross_ref WHERE characterId = :characterId AND itemId = :itemId")
    suspend fun getCharacterItem(characterId: Int, itemId: Int): CharacterItemCrossRef?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: Item)

    @Transaction
    suspend fun insertOrUpdateItemWithCharacter(item: Item, characterId: Int, quantity: Int) {
        // Primero, inserta o actualiza el ítem
        insertItem(item)

        // Luego, inserta o actualiza la relación en la tabla intermedia
        val crossRef = CharacterItemCrossRef(
            characterId = characterId,
            itemId = item.id,
            quantity = quantity
        )
        insertItemToCharacter(crossRef)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemToCharacter(crossRef: CharacterItemCrossRef)

    @Query("DELETE FROM item_table WHERE id = :itemId")
    suspend fun deleteItemById(itemId: Int)

    @Delete
    suspend fun deleteItemFromCharacter(characterItem: CharacterItemCrossRef)



}