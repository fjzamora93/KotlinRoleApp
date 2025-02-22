package com.unir.sheet.domain.repository

import com.unir.sheet.data.model.Item

interface ItemRepository {

    // Obtener todos los ítems
    suspend fun fetchItems(): Result<List<Item>>

    suspend fun getItemsByCharacterId(characterId: Int): Result<List<Item>>

    suspend fun getItemByCharacterIdAndItemId(characterId: Int, itemId: Int) : Result<Item>

    // Eliminar un ítem de un personaje (lo que implica su destrucción)
    suspend fun deleteItem(item: Item)

    // Modificar un Item (como añadirlo a un personaje)
    suspend fun insertOrUpdate(item: Item)

//    // Obtener ítems por nombre
//    suspend fun getItemsByName(name: String): List<Item>
//
//    // Obtener ítems por categoría
//    suspend fun getItemsByCategory(category: String): List<Item>
//
//    // Obtener ítems por su valor en oro
//    suspend fun getItemsByGoldValue(goldValue: Int): List<Item>
    //     suspend fun getItemsByCustomFilter(filterJson: String): List<Item>

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


}
