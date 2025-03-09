package com.unir.sheet.data.repository

import android.util.Log
import com.unir.sheet.data.local.dao.CharacterDao
import com.unir.sheet.data.local.dao.SkillDao
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * LAS CLASES DE ESTE REPOSITORIO DEBEN IMPLEMENTAR LA INTERFAZ DEL REPOSITORIO domain.repository.CharacterRepository
 *
 * Además, deben aplicar la lógica para decidir si la petición se va a realizar al repositorio remoto o al local.
 * */
class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val apiService: ApiService,
    private val skillDao: SkillDao,

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

    /** MÉTODO QUE REALIZARÁ EL FETCH EN PARALELO, MIENTRAS SE DEVUELVE AL USUARIO LOS DATOS LOCALES */
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

                    // Insertar solo los personajes más recientes
                    if (charactersToInsert.isNotEmpty()) {
                        val remoteEntities = charactersToInsert.map { it.toCharacterEntity() }
                        println(remoteEntities) // Ver lo que se va a insertar
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
            val character = characterDao.getCharacterById(id)
                ?: throw NoSuchElementException("No se encontró el personaje con ID $id")
            Result.success(character)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Comienza con intentar guardarlo en la API, mientras que devuelve la inserción local
    override suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity> {
        return try {
            characterDao.insertCharacter(character)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiCharacter = character.toApiRequest()
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



}
