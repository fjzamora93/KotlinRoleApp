package com.unir.sheet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterItemCrossRef
import com.unir.sheet.data.model.Item

@Dao
interface ItemDao {


    @Transaction
    @Query("SELECT * FROM item_table WHERE id IN (SELECT itemId FROM character_item_cross_ref WHERE characterId = :characterId)")
    suspend fun getItemsByCharacterId(characterId: Long): List<Item>

    @Query("SELECT * FROM character_item_cross_ref WHERE characterId = :characterId AND itemId = :itemId")
    suspend fun getCharacterItem(characterId: Long, itemId: Int): CharacterItemCrossRef?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: Item)

    @Query("SELECT * FROM item_table WHERE id_game_session = :gameSessionId")
    suspend fun getItemsBySession(gameSessionId: Int): List<Item>

    @Query("DELETE FROM item_table WHERE id = :itemId")
    suspend fun deleteItemById(itemId: Int)

    @Delete
    suspend fun deleteItemFromCharacter(characterItem: CharacterItemCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Item>)

    /** TRANSACCIÓN Para añadir un objeto a un personaje (son 2 pasos, primero insertar el item y luego la relación entre el personaje y el item) */
    @Transaction
    suspend fun insertOrUpdateItemWithCharacter(item: Item, characterId: Long, quantity: Int) {
        insertItem(item)
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


}