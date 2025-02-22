package com.unir.sheet.ui.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
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

    private val _itemDetail = MutableLiveData<Item?>()
    val itemDetail: LiveData<Item?> = _itemDetail

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState


    fun getItemsByCharacterId(characterId: Int) {
        _loadingState.value = true
        viewModelScope.launch {
            val result = itemUseCases.getItemsByCharacterId(characterId)
            result.onSuccess { items ->
                _itemList.value = items
                _loadingState.value = false
                println("Inventario del personaje en el viewModel: ${_itemList.value}")
            }.onFailure {
                _loadingState.value = false
                println("Error al obtener los items del personaje con ID $characterId")
            }
        }
    }

    fun addItemToCharacter(
        currentCharacter: CharacterEntity,
        currentItem: Item,
    ){
        viewModelScope.launch {
            println("AÑADIENDO ${currentItem.name} AL PERSONAJE: ${currentCharacter.name}")
            itemUseCases.addItemToCharacter(currentCharacter, currentItem)
        }
    }

    fun removeItemFromCharacter(
        currentCharacter: CharacterEntity,
        currentItem: Item,
    ){
        viewModelScope.launch {
            itemUseCases.destroyItem(currentCharacter, currentItem)
            _itemList.value = _itemList.value?.filterNot { it == currentItem }
        }
    }

    fun sellItem(
        currentCharacter: CharacterEntity,
        currentItem: Item,
    ){
        viewModelScope.launch {
            itemUseCases.sellItem(currentCharacter, currentItem)
            _itemList.value = _itemList.value?.filterNot { it == currentItem }
        }
    }


    fun getItems() {
        viewModelScope.launch {
            val result = itemUseCases.fetchItems()
            result.onSuccess {
                    items ->
                _itemList.value = items
                println("Primer control de items en el view model" + items)
            }.onFailure {
                _loadingState.value = false
                println("Error al obtener los items desde la API")
            }

            println("LISTA ACTUALIZADA EN EL ITEM VIEW MODEL: ${itemList.value}")
        }
    }



}
