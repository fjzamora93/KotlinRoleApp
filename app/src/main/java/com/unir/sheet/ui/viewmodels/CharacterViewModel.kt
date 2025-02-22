package com.unir.sheet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.CharacterEntity
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

    // Generalmente usaríamos State para ambos, pero en este caso LiveData funciona mejor (con State no va a cambiar bien)
    private val _selectedCharacter = MutableLiveData<CharacterEntity?>()
    val selectedCharacter: LiveData<CharacterEntity?> = _selectedCharacter


    init {
        getAllCharacters()
    }

    // Función para obtener un personaje por ID
    fun getCharacterById(characterId: Int) {
        viewModelScope.launch {
            val rolCharacter = characterUseCases.getCharacterById(characterId)

            // Aquí puedes actualizar el UI con el personaje encontrado
            _selectedCharacter.value = rolCharacter
            println("Personaje: ${_selectedCharacter.value?.name} ")
        }
    }

    // Función para obtener todos los personajes
    fun getAllCharacters() {
        viewModelScope.launch {
            val charactersList = characterUseCases.getAllCharacters() // Obtén la lista de personajes
            _characters.value = charactersList // Actualiza el estado con la lista obtenida
        }
    }



    // Función para insertar un nuevo personaje
    fun insertCharacter(characterEntity: CharacterEntity) {
        viewModelScope.launch {
            // Completar el personaje (por ejemplo, asignar valores predeterminados)
            characterEntity.completeCharacter()
            characterUseCases.insertCharacter(characterEntity)

            // Fetch the updated list of characters
            val updatedCharacters = characterUseCases.getAllCharacters()
            _characters.value = updatedCharacters

            _selectedCharacter.value = updatedCharacters.lastOrNull()
            println("Personaje seleccionado dentro de CharacterViewModel: ${_selectedCharacter.value}")
        }
    }



    // Función para actualizar un personaje
    fun updateCharacter(characterEntity: CharacterEntity) {
        viewModelScope.launch {
            characterUseCases.updateCharacter(characterEntity)
            _selectedCharacter.value = characterEntity
            println("Personaje actualizado: $characterEntity")
            _selectedCharacter.value = characterUseCases.getCharacterById(characterEntity.id!!)
        }
    }

    // Función para eliminar un personaje
    fun deleteCharacter(characterEntity: CharacterEntity) {
        viewModelScope.launch {
            characterUseCases.deleteCharacter(characterEntity)
            println("Personaje eliminado: $characterEntity")
            val updatedCharacters = characterUseCases.getAllCharacters()
            _characters.value = updatedCharacters
        }
    }



}
