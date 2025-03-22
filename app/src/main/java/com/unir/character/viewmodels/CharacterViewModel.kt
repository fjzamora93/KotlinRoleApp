package com.unir.character.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.domain.usecase.character.CharacterUseCases
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

    // PERSONAJE EN USO
    private val _selectedCharacter = MutableStateFlow<CharacterEntity?>(null)
    val selectedCharacter: MutableStateFlow<CharacterEntity?> = _selectedCharacter



    // Función para actualizar un personaje
    fun updateCharacter(characterEntity: CharacterEntity) {
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
                Log.e("Error en la inserción","Error al actualizar personaje: ${errorMessage.value}")
            }
        }
    }

    // Función para obtener un personaje por ID
    fun getCharacterById(characterId: Long) {
        _loadingState.value = true
        viewModelScope.launch {
            val result = characterUseCases.getCharacterById(characterId)
            result.onSuccess { rolCharacter ->
                if (rolCharacter != null) {
                    _selectedCharacter.value = rolCharacter
                }
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
