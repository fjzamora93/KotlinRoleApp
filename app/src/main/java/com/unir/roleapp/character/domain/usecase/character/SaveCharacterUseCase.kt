package com.roleapp.character.domain.usecase.character

import android.util.Log
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.ui.screens.characterform.components.PersonalityTestForm
import javax.inject.Inject


// Actualizar personaje
class SaveCharacterUseCase @Inject constructor(
    private val createCharacterUseCase: CreateCharacterUseCase,
    private val updateCharacterUseCase: UpdateCharacterUseCase
) {
    suspend operator fun invoke(character: CharacterEntity, form : PersonalityTestForm): Result<CharacterEntity> {
        return if (character.id == 0L) {
            createCharacterUseCase(character, form)
        } else {
            updateCharacterUseCase(character)
        }
    }
}