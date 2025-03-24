package com.unir.character.data.repository

import android.util.Log
import com.unir.character.data.dao.CharacterDao
import com.unir.character.data.dao.SkillDao
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.remote.toSkill
import com.unir.character.data.service.CharacterApiService
import com.unir.character.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Además, deben aplicar la lógica para decidir si la petición se va a realizar al repositorio remoto o al local.
 * */
class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val apiService: CharacterApiService,

    ) : CharacterRepository {


    /** Este método hace el fetch en segundo plano, pero la vista no se va a refrescar automáticamente, habría que refrescarla manualmente */
    override suspend fun getCharactersByUserId(userId: Int): Result<List<CharacterEntity>> {
        return try {
            val localCharacters = characterDao.getCharactersByUserId(userId)
            CoroutineScope(Dispatchers.IO).launch {
                fetchAndUpdateCharacters(userId)
            }
            return Result.success(localCharacters)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /** TODO: AJUSTAR LA SINCRONIZACIÓN REMOTA, AHORA MISMO NO OBTIENE LAS HABILIDADES CORRECTAMENTE, SOLO EL PERSONAJE
     * TODO : LAS HABILIDADES QUE SE DEVUELVAN CUANDO SE ACCEDA AL DETALLE, NO EN LA LISTA COMPLETA. El fetch genérico solo necesita al character, no sus skills
      */
    private suspend fun fetchAndUpdateCharacters(userId: Int) {
        try {
            val remoteResponse = apiService.getCharactersByUserId(userId)
            if (remoteResponse.isSuccessful) {
                val remoteCharacters = remoteResponse.body()
                if (!remoteCharacters.isNullOrEmpty()) {
                    val localCharacters = characterDao.getCharactersByUserId(userId)
                    val localCharacterMap = localCharacters.associateBy { it.id }
                    val charactersToInsert = remoteCharacters.filter { remoteCharacter ->
                        val localCharacter = localCharacterMap[remoteCharacter.id]
                        localCharacter == null || remoteCharacter.updatedAt > localCharacter.updatedAt
                    }

                    if (charactersToInsert.isNotEmpty()) {
                        val remoteEntities = charactersToInsert.map { it.toCharacterEntity() }
                        characterDao.insertAll(remoteEntities)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error al insertar los remotos en la local", "No se pudieron insertar los personajes", e)
        }
    }



    /** LOs personajes por id los buscará directamente en el DAO. Si no lo encuentra, levantará una excepción  */
    override suspend fun getCharacterById(id: Long): Result<CharacterEntity> {
        return try {
            val character = characterDao.getCharacterById(id) ?: throw NoSuchElementException("No se encontró el personaje con ID $id")
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // IMPORTANTE: Añadir siempre las Skills para que lleguen correctamente al backend
    override suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity> {
        return try {
            characterDao.insertCharacter(character)
            val skillsCrossRef = characterDao.getCharacterSkills(character.id)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiCharacter = character.toApiRequest()
                    apiCharacter.skills = skillsCrossRef
                    apiService.saveCharacter(apiCharacter)
                } catch (e: Exception) {
                    Log.e("API Error", "Fallo al guardar en la API", e)
                }
            }
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun deleteCharacter(character: CharacterEntity): Result<Unit> {
        return try {
            CoroutineScope(Dispatchers.IO).async {
                try {
                    apiService.deleteCharacter(character.id)
                } catch (e: Exception) {
                    Log.e("API Error", "Fallo al eliminar en la API", e)
                }
            }
            characterDao.deleteCharacter(character)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TODO: Añadir a la cola de sincronización para la actualización remota
    override suspend fun saveCharacterWithSKills(
        character: CharacterEntity,
        skillCrossRef: List<CharacterSkillCrossRef>
    ): Result<CharacterEntity> {
        return try{
            characterDao.insertCharacterWithSkills(character, skillCrossRef)
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
