package com.unir.character.viewmodels
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.Skill
import com.unir.character.domain.usecase.skill.SkillUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SkillViewModel  @Inject constructor(
    private val skillUseCases: SkillUseCases,
) :  ViewModel() {

    private val _skillList = MutableStateFlow<List<Skill>>(emptyList())
    val skillList: StateFlow<List<Skill>> get() = _skillList


    fun getAllSKills(){
        viewModelScope.launch {
            val result = skillUseCases.getAllSkills()
            result.onSuccess {
                _skillList.value = it
                println("Skills: ${skillList.value}")
            }.onFailure {
                println("Error ${it.message} al obtener las skills")
            }
        }
    }

    fun getSkillsFromCharacter(
        character: CharacterEntity,
    ){
        viewModelScope.launch {
            val result = skillUseCases.addDefaultSkills(character)
            result.onSuccess {
                _skillList.value = it
                println("Skills en el viewMOdel: ${skillList.value}")
            }.onFailure {
                println("Error ${it.message} al obtener las skills")
            }
        }
    }

}