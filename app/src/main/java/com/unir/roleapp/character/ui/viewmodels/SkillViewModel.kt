package com.roleapp.character.ui.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.SkillValue
import com.roleapp.character.domain.usecase.skill.SkillUseCases
import com.roleapp.character.domain.usecase.skill.SkillValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SkillViewModel @Inject constructor(
    private val skillUseCases: SkillUseCases,
) : ViewModel() {

    private val _skillList = MutableStateFlow<List<SkillValue>>(emptyList())
    val skillList: StateFlow<List<SkillValue>> get() = _skillList

    private val _isValid = MutableStateFlow(false)
    val isValid: StateFlow<Boolean> get() = _isValid

    // Para manejar errores
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _pointsAvailable = MutableStateFlow(0)
    val pointsAvailable: StateFlow<Int> get() = _pointsAvailable

    init {
        fetchSkills()
    }

    fun getSkillsFromCharacter(character: CharacterEntity) {
        viewModelScope.launch {
            val result = skillUseCases.getSkillsFromCharacter(character.id)
            result.onSuccess { skills ->
                _skillList.value = skills
                _errorMessage.value = null
                println("Skills en el ViewModel: ${skillList.value}")
                skillUseCases.validateSkillValue(character, _skillList.value)
            }.onFailure { error ->
                _errorMessage.value = "Error al obtener las habilidades: ${error.message}"
                println("Error: ${error.message}")
            }
        }
    }

    fun updateSkills(character: CharacterEntity, skillList: List<SkillValue>) {
        viewModelScope.launch {
            val result = skillUseCases.updateSkills(character, skillList)
            result.onSuccess {
                _errorMessage.value = null // Limpiar el mensaje de error
                println("Habilidades actualizadas correctamente")
            }.onFailure { error ->
                _errorMessage.value = "Error al actualizar las habilidades: ${error.message}"
                println("Error: ${error.message}")
            }
        }
    }
    fun validateSkills(character: CharacterEntity, skillValues: List<SkillValue>) {
        viewModelScope.launch {

            val result = skillUseCases.validateSkillValue(character, skillValues)
            result.onSuccess { validationResult ->
                when (validationResult) {
                    is SkillValidationResult.Success -> {
                        _isValid.value = true
                        _errorMessage.value = null
                        _pointsAvailable.value = validationResult.puntosDisponibles
                    }
                    is SkillValidationResult.Error -> {
                        _isValid.value = false
                        _errorMessage.value = validationResult.message
                        _skillList.value = validationResult.correctedSkills

                        // Recalcular puntos disponibles
                        _pointsAvailable.value = validationResult.puntosDisponibles
                    }
                }
            }.onFailure { error ->
                _isValid.value = false
                _errorMessage.value = "Error al validar las habilidades: ${error.message}"
            }
        }
    }

    // Recibe un índice
    fun updateSkillValue(index: Int, newValue: Int, character:CharacterEntity) {
        _skillList.value = _skillList.value.toMutableList().apply {
            this[index] = this[index].copy(value = newValue)
            updateSkills(character, this)
        }
    }


    private fun fetchSkills(){
        viewModelScope.launch {
            val result = skillUseCases.fetchSkills()
            result.onSuccess { skills ->
                _errorMessage.value = null
                println("Skills en el ViewModel: ${skillList.value}")
            }.onFailure { error ->
                _errorMessage.value = "Error al obtener las habilidades: ${error.message}"
                println("Error: ${error.message}")
            }
        }
    }
}