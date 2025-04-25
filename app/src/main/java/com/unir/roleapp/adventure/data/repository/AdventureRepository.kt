package com.unir.roleapp.adventure.data.repository

import com.unir.roleapp.adventure.domain.model.Adventure
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureRequest

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
    suspend fun deleteAdventure(adventureId: String)
}
