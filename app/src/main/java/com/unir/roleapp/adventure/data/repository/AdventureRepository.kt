package com.unir.roleapp.adventure.data.repository

import com.unir.roleapp.adventure.domain.model.Adventure
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface AdventureRepository {

    /**
     * Crea una nueva aventura con los datos del [request].
     *
     * @param request contenedor de título, descripción y género.
     * @return [Result.Success] con el objeto [Adventure] (incluye su nuevo ID)
     *         o [Result.Failure] con la excepción si algo falla.
     */
    suspend fun createAdventure(request: CreateAdventureRequest): Result<Adventure>

    /**
     * Obtiene todas las aventuras almacenadas.
     *
     * @return [Result.Success] con la lista de [Adventure]
     *         o [Result.Failure] si hay algún error al cargar.
     */
    suspend fun getAdventures(): Result<List<Adventure>>

    suspend fun getAdventure(adventureId: String): Result<Adventure>
    suspend fun deleteAdventure(adventureId: String): Unit

    /*
    fun getCharactersFlow(adventureId: String): Flow<List<CharacterSession>> = callbackFlow {
        val ref = firestore.collection("adventures").document(adventureId)
        val sub = ref.addSnapshotListener { snap, _ ->
            val chars = snap?.get("characters") as? List<Map<String,Any>> ?: emptyList()
            val mapped = chars.map { map -> map.toCharacterSession() }
            trySend(mapped)
        }
        awaitClose { sub.remove() }
    }

     */
}
