package com.unir.sheet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterItemCrossRef
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.data.model.Item

@Dao
interface ItemDao {


    /**Transacción única para obtener la relación entre persnoajes e items */
    @Query("SELECT * FROM item_table WHERE id IN (SELECT itemId FROM character_item_cross_ref WHERE characterId = :characterId)")
    suspend fun getItemsByCharacterId(characterId: Long): List<Item>

    @Query("SELECT * FROM character_item_cross_ref WHERE characterId = :characterId")
    suspend fun getCharacterItemsByCharacterId(characterId: Long): List<CharacterItemCrossRef>

    @Transaction
    suspend fun getItemsDetailByCharacter(characterId: Long): List<CharacterItemDetail> {
        val items = getItemsByCharacterId(characterId)
        val itemsCrossRef = getCharacterItemsByCharacterId(characterId).associateBy { it.itemId }

        return items.map { item ->
            CharacterItemDetail(item, characterId, itemsCrossRef[item.id]?.quantity ?: 0)
        }
    }

    @Transaction()
    suspend fun getItemDetail(characterId: Long, itemId: Int): CharacterItemDetail {
        val item = getItemById(itemId)
        val quantity = getCharacterItemsByCharacterId(characterId).find { it.itemId == itemId }?.quantity ?: 0
        return CharacterItemDetail(item, characterId, quantity)
    }

    @Query("SELECT * FROM ITEM_TABLE WHERE id = :itemId ")
    suspend fun getItemById(itemId: Int): Item



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


    // Vender un objeto: Sumar el valor del item al oro del personaje
    @Query("""
        UPDATE character_entity_table
        SET gold = gold + (
            SELECT item_table.goldValue 
            FROM item_table 
            INNER JOIN character_item_cross_ref ON item_table.id = character_item_cross_ref.itemId
            WHERE character_item_cross_ref.characterId = :characterId AND character_item_cross_ref.itemId = :itemId
        ),
        updatedAt = strftime('%s', 'now') * 1000
        WHERE id = :characterId
    """)
    suspend fun sellItem(characterId: Long, itemId: Int)

    // Comprar un objeto: Multiplicar el oro del personaje por el valor del item
    @Query("""
        UPDATE character_entity_table
        SET gold = gold - (
            SELECT item_table.goldValue 
            FROM item_table 
            INNER JOIN character_item_cross_ref ON item_table.id = character_item_cross_ref.itemId
            WHERE character_item_cross_ref.characterId = :characterId AND character_item_cross_ref.itemId = :itemId
        ),
        updatedAt = strftime('%s', 'now') * 1000
        WHERE id = :characterId
    """)
    suspend fun buyItem(characterId: Long, itemId: Int)

}