package com.unir.character.domain.usecase.character

import android.util.Log
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.usecase.character.generateutils.generateStats
import com.unir.character.ui.screens.skills.PersonalityTestForm
import javax.inject.Inject


// Actualizar personaje
class SaveCharacterUseCase @Inject constructor(
    private val createCharacterUseCase: CreateCharacterUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase
) {
    suspend operator fun invoke(character: CharacterEntity, form : PersonalityTestForm): Result<CharacterEntity> {
        return if (character.id == 0L) {
            Log.d("SaveCharacterUseCase", "Creando personaje")
            createCharacterUseCase(character, form)
        } else {
            Log.d("SaveCharacterUseCase", "ACTUALIZANDO personaje existente")
            updateCharacterUseCase(character)
        }
    }
}