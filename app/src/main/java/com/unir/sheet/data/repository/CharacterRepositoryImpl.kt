package com.unir.sheet.data.repository

import android.util.Log
import com.unir.sheet.data.local.dao.CharacterDao
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * LAS CLASES DE ESTE REPOSITORIO DEBEN IMPLEMENTAR LA INTERFAZ DEL REPOSITORIO domain.repository.CharacterRepository
 *
 * Además, deben aplicar la lógica para decidir si la petición se va a realizar al repositorio remoto o al local.
 * */
class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val apiService: ApiService
) : CharacterRepository {


    override suspend fun getCharactersByUserId(userId: Int): Result<List<CharacterEntity>> {
        return try {
            // 1. Obtener datos locales y devolverlos de inmediato si existen
            val localCharacters = characterDao.getCharactersByUserId(userId)
            if (localCharacters.isNotEmpty()) {

                // Lanzamos el fetch en una corrutina para no bloquear el flujo principal
                CoroutineScope(Dispatchers.IO).launch {
                    fetchAndUpdateCharacters(userId)
                }
                return Result.success(localCharacters)
            }

            // 2. Si no hay datos locales, hacer la llamada remota
            val remoteResponse = apiService.getCharactersByUserId(userId)
            if (remoteResponse.isSuccessful) {
                val remoteCharacters = remoteResponse.body()
                if (!remoteCharacters.isNullOrEmpty()) {
                    val remoteEntities = remoteCharacters.map { it.toCharacterEntity() }
                    characterDao.insertAll(remoteEntities)
                    return Result.success(remoteEntities)
                }
            }

            Result.success(emptyList()) // Si no hay datos ni remotos ni locales
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
                    val remoteEntities = remoteCharacters.map { it.toCharacterEntity() }
                    characterDao.insertAll(remoteEntities)
                }
            }
        } catch (e: Exception) {
            Log.e("Error al conectar con la API", "NO se ha logrado sincronizar los datos de la API remota")
        }
    }


    override suspend fun getCharacterById(id: Int): Result<CharacterEntity?> {
        return try {
            val result = apiService.getCharacterById(id)
            if (result.isSuccessful) {
                val character = result.body()
                if (character != null) {
                    Result.success(character.toCharacterEntity())
                } else {
                    Result.success(null) // Return null if no character is found
                }
            } else {
                Result.failure(Exception("Error en la respuesta: ${result.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity> {
        return try {
            val result = apiService.saveCharacter(character.toApiRequest()) // Convert to CharacterResponse
            if (result.isSuccessful) {
                val savedCharacter = result.body()
                if (savedCharacter != null) {
                    Result.success(savedCharacter.toCharacterEntity()) // Convert back to CharacterEntity
                } else {
                    Result.failure(Exception("Error al guardar el personaje: Respuesta vacía")) // Return an error
                }
            } else {
                Result.failure(Exception("Error en la respuesta: ${result.code()}")) // Return an error
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun deleteCharacter(character: CharacterEntity): Result<Unit> {
        return try {
            val result = apiService.deleteCharacter(character.id!!)
            if (result.isSuccessful) {
                characterDao.deleteCharacter(character)
                Result.success(Unit) // Return success if both operations are successful
            } else {
                Result.failure(Exception("Error al eliminar el personaje en el servidor: ${result.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}
