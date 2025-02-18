package com.unir.sheet.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.unir.sheet.data.local.model.CharacterItemCrossRef
import com.unir.sheet.data.local.model.RolCharacter


@Dao
interface CharacterDao {

    // QUERYS SIMPLES
    @Query("SELECT * FROM rolCharacterTable")
    suspend fun getAllCharacters(): List<RolCharacter>

    @Transaction
    @Query("SELECT * FROM rolCharacterTable WHERE id = :characterId")
    suspend fun getCharacter(characterId: Int): RolCharacter?

    @Insert
    suspend fun insertCharacter(character: RolCharacter)

    @Update
    suspend fun updateCharacter(character: RolCharacter)

    @Delete
    suspend fun deleteCharacter(character: RolCharacter)



    // QUERYS DE CROSS REF
    @Transaction
    @Query("SELECT * FROM rolCharacterTable WHERE id = :characterId")
    suspend fun getCharacterWithRelations(characterId: Int): RolCharacterWithAllRelations?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToCharacter(characterItemCrossRef: CharacterItemCrossRef)

    @Delete
    suspend fun removeItemFromCharacter(characterCrossRef: CharacterItemCrossRef)


}
