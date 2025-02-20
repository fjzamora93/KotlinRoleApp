package com.unir.sheet.ui.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.local.model.Item
import com.unir.sheet.data.local.model.RolCharacter
import com.unir.sheet.data.local.repository.LocalCharacterRepository
import com.unir.sheet.data.local.repository.LocalSkillRepository
import com.unir.sheet.data.remote.repository.RemoteCustomItemRepository
import com.unir.sheet.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val remoteItemRepository: RemoteCustomItemRepository,
    private val localCharacterRepository: LocalCharacterRepository

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
            localCharacterRepository.addItemToCharacter(currentCharacter, currentItem)
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
