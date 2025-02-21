package com.unir.sheet.ui.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.RolCharacter
import com.unir.sheet.data.repository.CharacterRepositoryImpl
import com.unir.sheet.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val remoteItemRepository: ItemRepository,
    private val characterRepository: CharacterRepositoryImpl

) : ViewModel() {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> get() = _itemList

    private val _itemDetail = MutableLiveData<Item?>()
    val itemDetail: LiveData<Item?> = _itemDetail


    fun addItemToCharacter(
        currentCharacter: RolCharacter,
        currentItem: Item,
//        onSuccess: (List<Item>) -> Unit = { },
//        onError: () -> Unit = { }
    ){
        viewModelScope.launch {
            println("AÑADIENDO ${currentItem.name} AL PERSONAJE: ${currentCharacter.name}")
            characterRepository.addItemToCharacter(currentCharacter, currentItem)
        }
    }

    fun removeItemFromCharacter(
        currentCharacter: RolCharacter,
        currentItem: Item,
    ){
        viewModelScope.launch {
            characterRepository.removeItemFromCharacter(currentCharacter, currentItem)
            _itemList.value = _itemList.value?.filterNot { it == currentItem }
        }

    }

    fun getItems(
        name: String = "",
        onSuccess: (List<Item>) -> Unit = { },
        onError: () -> Unit = { }
    ) {
        viewModelScope.launch {
            val result = remoteItemRepository.fetchItems()
            result.onSuccess {
                    items ->
                _itemList.value = items
                onSuccess(items)
            }.onFailure {
                onError()
            }

            println("LISTA ACTUALIZADA EN EL ITEM VIEW MODEL: ${itemList.value}")
        }
    }



}
