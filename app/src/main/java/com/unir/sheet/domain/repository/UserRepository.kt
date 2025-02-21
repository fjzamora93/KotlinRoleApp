package com.unir.sheet.domain.repository

import com.unir.sheet.data.model.User

interface UserRepository {

    // Obtener usuario por ID
    suspend fun getUserById(id: Long): User?

    // Obtener usuario por correo electrónico y contraseña
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    // Agregar un nuevo usuario
    suspend fun addNewUser(user: User): User

    // Actualizar un usuario existente
    suspend fun updateExistingUser(user: User): User

    // Eliminar un usuario
    suspend fun deleteUser(id: Long)
}
