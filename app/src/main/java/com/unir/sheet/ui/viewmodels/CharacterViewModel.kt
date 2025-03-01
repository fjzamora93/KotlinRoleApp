package com.unir.sheet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.di.LocalUserViewModel
import com.unir.sheet.domain.usecase.character.CharacterUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterUseCases: CharacterUseCases,
) : ViewModel() {

    // Usamos `mutableStateOf` para el listado de personajes
    private val _characters = MutableStateFlow<List<CharacterEntity>>(emptyList())
    val characters: StateFlow<List<CharacterEntity>> get() = _characters.asStateFlow()

    // Para manejar errores
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // Para mostrar un loading state
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    // Generalmente usaríamos State para ambos, pero en este caso LiveData funciona mejor (con State no va a cambiar bien)
    private val _selectedCharacter = MutableLiveData<CharacterEntity?>()
    val selectedCharacter: LiveData<CharacterEntity?> = _selectedCharacter



    // Función para obtener un personaje por ID
    fun getCharacterById(characterId: Int) {
        _loadingState.value = true
        viewModelScope.launch {
            val result = characterUseCases.getCharacterById(characterId)
            result.onSuccess { rolCharacter ->
                _selectedCharacter.value = rolCharacter
                _loadingState.value = false
                println("Personaje encontrado: ${rolCharacter?.name}")
            }.onFailure {
                _loadingState.value = false
                _errorMessage.value = it.message
                println("Error al obtener el personaje con ID $characterId")
            }
        }
    }

    // Función para obtener todos los personajes
    fun getCharactersByUserId(userId: Int) {
        _loadingState.value = true
        viewModelScope.launch {
            val result = characterUseCases.getCharactersByUserId(userId)
            result.onSuccess { charactersList ->
                _characters.value = charactersList
                _loadingState.value = false
            }.onFailure {
                _loadingState.value = false
                _errorMessage.value = it.message
                println("Error al obtener los personajes")
            }
        }
    }



    // Función para actualizar un personaje
    fun updateCharacter(characterEntity: CharacterEntity) {
        println("Comenzando a actualizar personaje: ${characterEntity}")
        _loadingState.value = true
        viewModelScope.launch {
            val result = characterUseCases.updateCharacter(characterEntity)
            result.onSuccess {
                _selectedCharacter.value = characterEntity
                _loadingState.value = false
                println("Personaje actualizado: ${characterEntity.name}")
            }.onFailure {
                _loadingState.value = false
                _errorMessage.value = it.message
                println("Error al actualizar el personaje")
            }
        }
    }

    fun deleteCharacter(characterEntity: CharacterEntity) {
        _loadingState.value = true
        viewModelScope.launch {
            val result = characterUseCases.deleteCharacter(characterEntity)
            result.onSuccess {
                val updatedCharacters = _characters.value.filter { it.id != characterEntity.id }
                _characters.value = updatedCharacters
                _loadingState.value = false
                println("Personaje eliminado: ${characterEntity.name}")
            }.onFailure {
                _loadingState.value = false
                _errorMessage.value = it.message
                println("Error al eliminar el personaje")
            }
        }
    }
}
