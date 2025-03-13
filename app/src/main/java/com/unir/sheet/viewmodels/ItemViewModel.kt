package com.unir.sheet.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.domain.usecase.item.ItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemUseCases: ItemUseCases,
) : ViewModel() {

    private val _itemList = MutableStateFlow<List<Item>>(emptyList())
    val itemList: StateFlow<List<Item>> get() = _itemList

    private val _itemsByCharacter = MutableStateFlow<List<CharacterItemDetail>>(emptyList())
    val itemsByCharacter: StateFlow<List<CharacterItemDetail>> get() = _itemsByCharacter

    private val _currentGold = MutableStateFlow<Int>(0)
    val currentGold: StateFlow<Int> get() = _currentGold

    private val _loadingState = MutableStateFlow<Boolean>(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState


    fun getItemsByCharacterId(characterId: Long) {
        _loadingState.value = true
        viewModelScope.launch {
            val result = itemUseCases.getItemsByCharacterId(characterId)
            result.onSuccess { items ->
                _itemsByCharacter.value = items
                _loadingState.value = false
                println("Inventario del personaje en el viewModel: ${_itemList.value}")
            }.onFailure {
                _loadingState.value = false
                println("Error ${it.message} al obtener los items del personaje con ID $characterId")
            }
        }
    }

    fun upsertItemToCharacter(
        currentCharacter: CharacterEntity,
        currentItem: Item,
    ){
        viewModelScope.launch {
            println("AÑADIENDO ${currentItem.name} AL PERSONAJE: ${currentCharacter.name}")
            val result = itemUseCases.upsertItemToCharacter(currentCharacter, currentItem)
            result.onSuccess { items ->
                _itemsByCharacter.value = items
                println("El objeto se ha añadido correctamente")
            }.onFailure { error ->
                println("Error al añadir el objeto: ${error.message}")
            }
        }
    }




    fun destroyItem(
        currentCharacter: CharacterEntity,
        currentItem: Item,
    ){
        viewModelScope.launch {
            println("AÑADIENDO ${currentItem.name} AL PERSONAJE: ${currentCharacter.name}")
            val result = itemUseCases.destroyItem(currentCharacter, currentItem)
            result.onSuccess { items ->
                _itemsByCharacter.value = items
                println("El objeto se ha añadido correctamente")
            }.onFailure { error ->
                println("Error al añadir el objeto: ${error.message}")
            }
        }
    }


    fun getItemsBySession(sessionId: Int) {
        viewModelScope.launch {
            val result = itemUseCases.getItemsBySession(sessionId)
            result.onSuccess {
                    items ->
                _itemList.value = items
            }.onFailure {
                _loadingState.value = false
                println("Error ${it.message} al obtener los items desde la API")
            }

            println("LISTA ACTUALIZADA EN EL ITEM VIEW MODEL: ${itemList.value}")
        }
    }

    // IMPLEMENTAR MÉTODO QUE PERMITA OBTENER EL ORO Y ASÍ TENERLO SIEMPRE ACTUALIZADO AL MARGEN DEL CHARACTER
    fun getCurrentGold(character: CharacterEntity){
        viewModelScope.launch {
//            val result = itemUseCases.getCurrentGold(character)
//            result.onSuccess {
//                    gold ->
//                _currentGold.value = gold
//            }.onFailure {
//                _loadingState.value = false
//                println("Error ${it.message} al obtener el oro")
//            }
        }
    }

    /**OBTIENE TODOS LOS OBJETOS DE PLANTILLA.
     * Los objetos de plantilla NO adminte POST, PUT O DELETE. Solamente son de lectura
     * Cuando el GM quiera personalizar el objeto, obtiene uno de plantilla y
     * devuelve una copia del objeto a la base de datos custom_item, con el ID del contexto de una sesión.
     * */
    fun fetchTemplateItems() {
        viewModelScope.launch {
            val result = itemUseCases.fetchTemplateItems()
            result.onSuccess {
                    items ->
                _itemList.value = items
            }.onFailure {
                _loadingState.value = false
                println("Error ${it.message} al obtener los items desde la API")
            }

            println("LISTA ACTUALIZADA EN EL ITEM VIEW MODEL: ${itemList.value}")
        }
    }



}
