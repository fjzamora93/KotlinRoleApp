package com.unir.sheet.ui.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.domain.usecase.item.ItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemUseCases: ItemUseCases,
) : ViewModel() {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> get() = _itemList

    private val _itemsByCharacter = MutableLiveData<List<CharacterItemDetail>>()
    val itemsByCharacter: LiveData<List<CharacterItemDetail>> get() = _itemsByCharacter

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState


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

    fun addItemToCharacter(
        currentCharacter: CharacterEntity,
        currentItem: Item,
    ){
        viewModelScope.launch {
            println("AÑADIENDO ${currentItem.name} AL PERSONAJE: ${currentCharacter.name}")
            val result = itemUseCases.addItemToCharacter(currentCharacter, currentItem)
            result.onSuccess {
                println("El objeto se ha añadido correctamente")
            }.onFailure { error ->
                println("Error al añadir el objeto: ${error.message}")
            }
        }
    }


    fun sellItem(
        currentCharacter: CharacterEntity,
        currentItem: Item,
    ){
        viewModelScope.launch {
            val result =   itemUseCases.sellItem(currentCharacter, currentItem)
            result.onSuccess {
                println("El objeto se vendió correctamente")
                _itemList.value = _itemList.value?.filterNot { it == currentItem }

            }.onFailure { error ->
                println("Error al vender el objeto: ${error.message}")
            }
        }
    }

    fun removeItemFromCharacter(
        currentCharacter: CharacterEntity,
        currentItem: Item,
    ){
        viewModelScope.launch {
            val result = itemUseCases.destroyItem(currentCharacter, currentItem)
            result.onSuccess {
                println("El objeto se ha eliminado correctamente")
                _itemList.value = _itemList.value?.filterNot { it == currentItem }
            }.onFailure { error ->
                println("Error al eliminar el objeto: ${error.message}")
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
