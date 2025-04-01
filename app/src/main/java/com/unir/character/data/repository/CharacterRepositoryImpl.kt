package com.unir.character.data.repository

import android.util.Log
import com.unir.character.data.dao.CharacterDao
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.service.CharacterApiService
import com.unir.character.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Además, deben aplicar la lógica para decidir si la petición se va a realizar al repositorio remoto o al local.
 * */
class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val apiService: CharacterApiService,

    ) : CharacterRepository {

        private lateinit var activeCharacter : CharacterEntity

    override suspend fun getActiveCharacter() : Result<CharacterEntity> {
        return Result.success(activeCharacter)
    }

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
                        characterDao.insertAll(charactersToInsert.map { it.toCharacterEntity() })
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error", "No se pudieron sincronizar los personajes", e)
        }
    }




    override suspend fun getCharacterById(id: Long): Result<CharacterEntity> {
        return try {
            val character = characterDao.getCharacterById(id) ?: throw NoSuchElementException("No se encontró el personaje con ID $id")
            activeCharacter = character
            Log.i("CharacterRepositoryImpl", "Personaje activo: $activeCharacter")
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    } /** LOs personajes por id los buscará directamente en el DAO. Si no lo encuentra, levantará una excepción  */


    // IMPORTANTE: Añadir siempre las Skills para que lleguen correctamente al backend
    override suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity> {
        return try {
            // 1. Obtener skills primero
            val skillsCrossRef = characterDao.getCharacterSkills(character.id)

            // 2. Intentar guardar en API usando tu función existente
            val apiResult = upsertCharacterToApi(character, skillsCrossRef)

            when {
                apiResult.isSuccess -> {
                    val apiEntity = apiResult.getOrThrow()
                    // Actualizar localmente con datos de la API
                    characterDao.updateCharacter(apiEntity)
                    Log.d("Sync", "Retornando personaje desde API")
                    Result.success(apiEntity)
                }
                else -> {
                    // Fallback a local
                    characterDao.updateCharacter(character)
                    Log.w("Sync", "Retornando personaje desde base de datos local (fallo API: ${apiResult.exceptionOrNull()?.message})")
                    Result.success(character)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveCharacterWithSKills(
        character: CharacterEntity,
        skillCrossRef: List<CharacterSkillCrossRef>
    ): Result<CharacterEntity> {
        return try {
            // 1. Intentar guardar en API usando tu función existente
            val apiResult = upsertCharacterToApi(character, skillCrossRef)

            // 2. Guardar localmente en cualquier caso (con datos de API si tuvo éxito)
            characterDao.insertCharacterWithSkills(character, skillCrossRef)

            // 3. Retornar el resultado apropiado
            if (apiResult.isSuccess) {
                Log.d("Sync", "Retornando personaje con skills desde API")
                apiResult
            } else {
                Log.w("Sync", "Retornando personaje con skills desde local (fallo API: ${apiResult.exceptionOrNull()?.message})")
                Result.success(character)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun upsertCharacterToApi(
        character: CharacterEntity,
        skillsCrossRef: List<CharacterSkillCrossRef>
    ): Result<CharacterEntity> = withContext(Dispatchers.IO) {
        try {
            val characterRequest = character.toApiRequest().apply {
                this.characterSkills = skillsCrossRef
            }

            Log.d("CharacterRepositoryImpl", "CharacterRepositoryImpl.upsertCharacterToApi: $characterRequest")

            val response = apiService.saveCharacter(characterRequest)

            if (!response.isSuccessful || response.body() == null) {
                Log.e("API Error", "Fallo al guardar en la API")
                return@withContext Result.failure(Exception("No ha sido posible guardar al personaje en la API"))
            }

            Result.success(response.body()!!.toCharacterEntity())
        } catch (e: Exception) {
            Log.e("API Error", "Fallo al guardar en la API", e)
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

}
