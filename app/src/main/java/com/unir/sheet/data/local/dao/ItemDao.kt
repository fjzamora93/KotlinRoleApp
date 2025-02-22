package com.unir.sheet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unir.sheet.data.model.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM itemTable")
    suspend fun getItemList(): List<Item>

    @Query("SELECT * FROM itemTable WHERE characterId = :characterId")
    suspend fun getItemByCharacterId(characterId: Int): List<Item>


    @Query("SELECT * FROM itemTable WHERE characterId = :characterId AND id = :itemId")
    suspend fun getItemByCharacterIdAndItemId(characterId: Int, itemId: Int): Item?

    @Query("SELECT * FROM itemTable WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)


}