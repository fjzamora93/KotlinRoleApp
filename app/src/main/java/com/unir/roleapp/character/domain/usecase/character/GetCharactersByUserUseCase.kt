package com.roleapp.character.domain.usecase.character

import android.util.Log
import com.roleapp.auth.domain.usecase.user.UserUseCase
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.domain.repository.CharacterRepository
import javax.inject.Inject


// Obtener personajes por User ID
class GetCharactersByUserUseCase @Inject constructor(
    private val repository: CharacterRepository,
    private val userUseCase: UserUseCase
) {
    suspend operator fun invoke(): Result<List<CharacterEntity>> {
        val userResult = userUseCase.getUser()
        val userId = userResult.getOrNull()?.id ?: return Result.failure(Exception("User not found"))


        val result = repository.getCharactersByUserId(userId)
        return if (result.isSuccess) {
            Result.success(result.getOrNull() ?: emptyList())
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}