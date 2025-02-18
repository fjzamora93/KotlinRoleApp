package com.unir.sheet.ui.viewmodels
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.local.model.Item
import com.unir.sheet.data.local.model.RolCharacter
import com.unir.sheet.data.local.model.Skill
import com.unir.sheet.data.local.repository.LocalCharacterRepository
import com.unir.sheet.data.local.repository.LocalSkillRepository
import com.unir.sheet.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SkillViewModel  @Inject constructor(
    private val localCharacterRepository: LocalCharacterRepository,
    private val skillRepository: LocalSkillRepository,
    @ApplicationContext private val context: Context
) :  ViewModel() {

    private val _skillList = mutableStateOf<List<Skill>>(emptyList())
    val skillList: State<List<Skill>> get() = _skillList


    fun getAllSKills(){
        viewModelScope.launch {
            _skillList.value = skillRepository.readFromJson(context)
            println("Skills: ${skillList.value}")
        }
    }

    fun getSkillsFromCharacter(
        characterId: Int,
        skillId: Int,
    ){
        viewModelScope.launch {
            skillRepository.fetchSkillsFromCharacter(characterId, skillId)
            println("Skills: ${skillList.value}")
        }
    }

}