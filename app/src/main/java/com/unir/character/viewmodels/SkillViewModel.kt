package com.unir.character.viewmodels
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.Skill
import com.unir.character.data.model.local.SkillValue
import com.unir.character.domain.usecase.skill.SkillUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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

    init {
        fetchSkills()
    }

    fun getSkillsFromCharacter(character: CharacterEntity) {
        viewModelScope.launch {
            val result = skillUseCases.getSkillsFromCharacter(character.id)
            result.onSuccess { skills ->
                _skillList.value = skills
                _errorMessage.value = null // Limpiar el mensaje de error
                println("Skills en el ViewModel: ${skillList.value}")
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

    fun validateSkills(character: CharacterEntity, skillValue: SkillValue) {
        viewModelScope.launch {
            val result = skillUseCases.validateSkillValue(character, skillValue)
            result.onSuccess { isValid ->
                _isValid.value = isValid
                _errorMessage.value = null // Limpiar el mensaje de error
                println("Validación de habilidad: $isValid")
            }.onFailure { error ->
                _errorMessage.value = "Error al validar la habilidad: ${error.message}"
                println("Error: ${error.message}")
            }
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