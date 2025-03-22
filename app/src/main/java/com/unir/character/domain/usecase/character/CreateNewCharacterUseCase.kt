package com.unir.character.domain.usecase.character

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.usecase.character.generateutils.generateStats
import javax.inject.Inject


// Actualizar personaje
class CreateNewCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(plainCharacter: CharacterEntity): Result<Unit> {
        // PROVISIONALMENTE, TODOS LOS PERSONAJES NUEVOS VAN A LA SESIÓN 0. QUITAR EN CUANTO EMPECEMOS A FUNCIONAR LA BASE DE DATOS LOCAL
        if (plainCharacter.gameSessionId == null){
            plainCharacter.gameSessionId = 0
        }

        val character = generateStats(plainCharacter)




        val result = repository.saveCharacter(character)
        return if (result.isSuccess) {
            Result.success(Unit)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Error al actualizar el personaje"))
        }
    }
}