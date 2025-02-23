package com.unir.sheet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unir.sheet.data.model.Item

@Dao
interface ItemDao {


    @Query("SELECT * FROM itemTable WHERE characterId = :characterId")
    suspend fun getItemsByCharacterId(characterId: Int): List<Item>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)


}