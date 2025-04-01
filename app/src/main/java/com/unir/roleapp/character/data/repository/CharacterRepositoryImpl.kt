package com.roleapp.character.data.repository

import android.util.Log
import com.roleapp.character.data.dao.CharacterDao
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.roleapp.character.data.service.CharacterApiService
import com.roleapp.character.domain.repository.CharacterRepository
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



    // TODO: AÑADIR VALIDACIÓN DE CUÁNDO FUE LA ÚLTIMA MODIFICACIÓN, PORQUE SI NO, SIEMPRE SOBREESCRIBE LA API
    override suspend fun getCharacterById(id: Long): Result<CharacterEntity> {
        return try {
            val localCharacter = characterDao.getCharacterById(id) ?: throw NoSuchElementException("No se encontró el personaje con ID $id")

            val characterResponse = apiService.getCharacterById(id)
            if (characterResponse.isSuccessful) {
                Log.d("CharacterRepositoryImpl", "CharacterRepositoryImpl.getCharacterById(): ${characterResponse.body()}")
                characterResponse.body()?.let { apiCharacter ->

                    val crossRefSkills = apiCharacter.skills.map { skillDTO ->
                        CharacterSkillCrossRef(
                            characterId = apiCharacter.id,
                            skillId = skillDTO.skill.id,
                            value = skillDTO.value
                        )
                    }

                    // TODO: Comprobar que el personaje local es el más actualizado, en caso contrario, actualizar el remoto.
                    if (apiCharacter.updatedAt >= localCharacter.updatedAt){
                        characterDao.insertCharacterWithSkills(apiCharacter.toCharacterEntity(), crossRefSkills)
                    }

                    // !! No devolver el localcharacter aún, ya que puede haberse actualizado.
                    activeCharacter = withContext(Dispatchers.IO) { characterDao.getCharacterById(id)!! }
                    return Result.success(activeCharacter)
                }
            }

            // Si la API no devolvió un resultado válido, intenta devolver el local
            activeCharacter = localCharacter
            Result.success(localCharacter)
        } catch (e: Exception) {
            // En caso de error, intenta recuperar el personaje local antes de fallar completamente
            characterDao.getCharacterById(id)?.let {
                activeCharacter = it
                return Result.success(it)
            }
            Result.failure(e)
        }
    }




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
