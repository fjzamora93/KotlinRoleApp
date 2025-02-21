package com.unir.sheet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.RolCharacter
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
    private val _characters = MutableStateFlow<List<RolCharacter>>(emptyList())
    val characters: StateFlow<List<RolCharacter>> get() = _characters.asStateFlow()

    // Generalmente usaríamos State para ambos, pero en este caso LiveData funciona mejor (con State no va a cambiar bien)
    private val _selectedCharacter = MutableLiveData<RolCharacter?>()
    val selectedCharacter: LiveData<RolCharacter?> = _selectedCharacter

    private val _selectedCharacterItems = MutableLiveData<List<Item>>()
    val selectedCharacterItems: LiveData<List<Item>> = _selectedCharacterItems


    init {
        getAllCharacters()
    }

    // Función para obtener un personaje por ID
    fun getCharacterById(characterId: Int) {
        viewModelScope.launch {
            val rolCharacter = characterUseCases.getCharacterById(characterId)
            val characterItems = characterUseCases.getCharacterWithRelations(characterId)?.items

            // Aquí puedes actualizar el UI con el personaje encontrado
            _selectedCharacter.value = rolCharacter
            _selectedCharacterItems.value = characterItems ?: emptyList()
            println("Personaje: ${_selectedCharacter.value?.name} " + "Inventario ${_selectedCharacterItems.value}")
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
    fun insertCharacter(rolCharacter: RolCharacter) {
        viewModelScope.launch {
            // Completar el personaje (por ejemplo, asignar valores predeterminados)
            rolCharacter.completeCharacter()
            characterUseCases.insertCharacter(rolCharacter)

            // Fetch the updated list of characters
            val updatedCharacters = characterUseCases.getAllCharacters()
            _characters.value = updatedCharacters

            _selectedCharacter.value = updatedCharacters.lastOrNull()
            println("Personaje seleccionado dentro de CharacterViewModel: ${_selectedCharacter.value}")
        }
    }



    // Función para actualizar un personaje
    fun updateCharacter(rolCharacter: RolCharacter) {
        viewModelScope.launch {
            characterUseCases.updateCharacter(rolCharacter)
            _selectedCharacter.value = rolCharacter
            println("Personaje actualizado: $rolCharacter")
            _selectedCharacter.value = characterUseCases.getCharacterById(rolCharacter.id!!)
        }
    }

    // Función para eliminar un personaje
    fun deleteCharacter(rolCharacter: RolCharacter) {
        viewModelScope.launch {
            characterUseCases.deleteCharacter(rolCharacter)
            println("Personaje eliminado: $rolCharacter")
            val updatedCharacters = characterUseCases.getAllCharacters()
            _characters.value = updatedCharacters
        }
    }



}
