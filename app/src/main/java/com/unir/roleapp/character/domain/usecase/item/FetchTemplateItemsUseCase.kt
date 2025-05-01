package com.unir.roleapp.character.domain.usecase.item

import com.unir.roleapp.character.data.model.local.Item
import com.unir.roleapp.character.domain.repository.ItemRepository
import javax.inject.Inject

/** DEntro del Use case podemos implementar la lógica por la cuál se van a recuperar los items.
 * De esta forma que nos tenemos que preocuparnos por hacerlo en el VIew Model.
 *
 * POr ejemplo, ahora vamos a recperar todos los Item, ¿pero y si en el futuro solo queremos que se devuelvan
 * los item que están dentro de una sesión concreta dentro de la API?
 *
 * En ese escenario, simplemente modificamos el UseCase, sin tener que preocuparnos de nada más.
*/
class FetchTemplateItemsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(): Result<List<Item>> {
        return repository.getTemplateItems()
    }
}
