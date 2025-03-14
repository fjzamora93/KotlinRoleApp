package com.unir.auth.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.unir.auth.data.model.User
import com.unir.character.data.model.local.CharacterEntity

@Dao
interface UserDao {

    // Transacción para insertar el usuario si no existe o actualizarlo si ya existe. ELimina localmente datos de otros usuarios.
    @Transaction
    suspend fun upsertUser(user: User) {
        deleteOtherUsers(user.id!!)

        val existingUser = getUser()

        if (existingUser == null) {
            saveUser(user)
        } else {
            updateUser(user)
        }
    }

    //TODO: El Ignore hace que solo se inserte en caso de que no exista previamente. Para tomar la decisión, utiliza el upserUser y no esté método.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): User?

    // Eliminamos todos los usuarios que no sean los activos en ese momento
    @Query("DELETE FROM user WHERE id != :userId")
    suspend fun deleteOtherUsers(userId: Int)

}