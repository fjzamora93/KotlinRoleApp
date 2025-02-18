package com.unir.sheet.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unir.sheet.data.local.model.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM itemTable")
    suspend fun getItemList(): List<Item>

    @Query("SELECT * FROM itemTable WHERE id = :itemId")
    suspend fun getTaskById(itemId: String): Item?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateTask(item: Item)

    @Delete
    suspend fun deleteTask(item: Item)
}