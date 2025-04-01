package com.roleapp.character.domain.usecase.character

import android.util.Log
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.domain.repository.CharacterRepository
import javax.inject.Inject

// UseCase para obtener el personaje activo, que retorna el ID del personaje
class GetActiveCharacterIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(): Result<CharacterEntity> {
        // Obtén el personaje activo (esto puede ser desde la base de datos o alguna lógica definida)
        val activeCharacter = characterRepository.getActiveCharacter().getOrNull()
        return if (activeCharacter != null) {
            Log.d("GetActiveCharacterIdUseCase", "Active character ID: ${activeCharacter}")
            Result.success(activeCharacter)
        } else {
            Result.failure(Exception("No active character found"))
        }
    }
}
