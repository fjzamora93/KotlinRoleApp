package com.unir.sheet.domain.repository

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.RolCharacter

interface ItemRepository {

    // Obtener todos los ítems
    suspend fun getAllItems(): List<Item>

    // Obtener ítems por nombre
    suspend fun getItemsByName(name: String): List<Item>

    // Obtener ítems por categoría
    suspend fun getItemsByCategory(category: String): List<Item>

    // Obtener ítems por su valor en oro
    suspend fun getItemsByGoldValue(goldValue: Int): List<Item>

    /** TODO: Sin concretar el fitro, pero funciona así:
     *
     * val filterJson = """
     *     {
     *         "name": "Sword",
     *         "category": "Weapon",
     *         "minGoldValue": 10
     *     }
     * """
     * val items = itemRepository.getItemsByCustomFilter(filterJson)
     */
    suspend fun getItemsByCustomFilter(filterJson: String): List<Item>

    // Eliminar un ítem de un personaje
    suspend fun deleteItemFromCharacter(character: RolCharacter, item: Item)

    // Añadir un ítem a un personaje
    suspend fun addItemToCharacter(character: RolCharacter, item: Item)
}
