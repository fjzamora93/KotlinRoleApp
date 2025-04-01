package com.unir.character.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.domain.usecase.character.CharacterUseCases
import com.unir.character.ui.screens.characterform.components.PersonalityTestForm
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

    // Estado de confirmación de guardado
    private val _saveState = MutableStateFlow<Long?>(null)
    val saveState: StateFlow<Long?> = _saveState

    // PERSONAJE EN USO
    private val _selectedCharacter = MutableStateFlow<CharacterEntity?>(null)
    val selectedCharacter: MutableStateFlow<CharacterEntity?> = _selectedCharacter

    init{
        getCharactersByUser()
    }

    // Función para obtener todos los personajes de un usuario. EL usuario se obtiene en el caso de uso, no en el viewmodel
    fun getCharactersByUser() {
        _loadingState.value = true
        viewModelScope.launch {
            val result = characterUseCases.getCharactersByUserId()
            result.onSuccess { charactersList ->
                _characters.value = charactersList
                _loadingState.value = false
            }.onFailure {
                _loadingState.value = false
                _errorMessage.value = it.message
                println("Error al obtener los personajes. ${_errorMessage.value}")
            }
        }
    }


    // Función para guardar un personaje
    fun saveCharacter(
        characterEntity: CharacterEntity,
        form : PersonalityTestForm = PersonalityTestForm()
    ) {
        _loadingState.value = true
        viewModelScope.launch {
            val result = characterUseCases.saveCharacter(characterEntity, form)
            result.onSuccess { savedCharacter ->
                _selectedCharacter.value = savedCharacter
                _loadingState.value = false
                _saveState.value = savedCharacter.id
                println("Personaje creado / actualizado: ${characterEntity.name}")
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
            println("Personaje anterior: ${_selectedCharacter.value}")
            val result = characterUseCases.getCharacterById(characterId)
            result.onSuccess { rolCharacter ->
                if (rolCharacter != null) {
                    _selectedCharacter.value = rolCharacter
                    println("Cambiando personaje en uso: ${_selectedCharacter.value}")

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



    fun deleteCharacter(characterEntity: CharacterEntity) {
        _loadingState.value = true
        viewModelScope.launch {
            val result = characterUseCases.deleteCharacter(characterEntity)
            result.onSuccess {
                _characters.value = _characters.value.toList().filter { it.id != characterEntity.id }
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
