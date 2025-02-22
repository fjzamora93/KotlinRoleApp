package com.unir.sheet.data.repository

import com.unir.sheet.data.local.dao.CharacterDao
import com.unir.sheet.data.local.dao.RolCharacterWithAllRelations
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.domain.repository.CharacterRepository
import javax.inject.Inject


/**
 * LAS CLASES DE ESTE REPOSITORIO DEBEN IMPLEMENTAR LA INTERFAZ DEL REPOSITORIO domain.repository.CharacterRepository
 *
 * Además, deben aplicar la lógica para decidir si la petición se va a realizar al repositorio remoto o al local.
 * */
class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
) : CharacterRepository {

    override suspend fun getAllCharacters(): List<CharacterEntity> {
        return characterDao.getAllCharacters()
    }

    /** TODO : Pendiente de implementar en el dao la búsqueda por usuario */
    override suspend fun getCharacterByUserId(userId: Int): List<CharacterEntity> {
        return characterDao.getAllCharacters()
    }

    override suspend fun getCharacterById(id: Int): CharacterEntity? {
        return characterDao.getCharacter(id)
    }

    override suspend fun insertCharacter(character: CharacterEntity) {
        characterDao.insertCharacter(character)
    }

    override suspend fun updateCharacter(character: CharacterEntity) {
        character.calculateHp()
        characterDao.updateCharacter(character)
    }

    override suspend fun deleteCharacter(character: CharacterEntity) {
        characterDao.deleteCharacter(character)
    }

    // TODAS LAS RELACIONES
    override suspend fun getCharacterWithRelations(characterId: Int): RolCharacterWithAllRelations? {
        return characterDao.getCharacterWithRelations(characterId)
    }



}
