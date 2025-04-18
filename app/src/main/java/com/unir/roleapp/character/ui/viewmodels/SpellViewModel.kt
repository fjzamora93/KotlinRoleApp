package com.roleapp.character.ui.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.Spell
import com.roleapp.character.domain.usecase.spell.SpellUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SpellViewModel @Inject constructor(
    private val spellUseCase: SpellUseCases,
) : ViewModel(){

    private val _spellList = MutableLiveData<List<Spell>>()
    val spellList: LiveData<List<Spell>> get() = _spellList


    // APlica un filtro de acuerdo al personaje
    fun getSpellsForCharacter(
        currentCharacter: CharacterEntity,
    ){
        viewModelScope.launch {
            val result = spellUseCase.getSpellsByLevelAndRoleClass(currentCharacter)
            result.onSuccess {
                    spells -> _spellList.value = spells

            }.onFailure {
                println("ALgo salió mal: ${it.message}")
            }
        }
    }


    fun getAllSpells(){
        viewModelScope.launch {
            val result = spellUseCase.getAllSpells()
            result.onSuccess {
                    spells -> _spellList.value = spells
            }.onFailure {
                println("ALgo salió mal: ${it.message}")
            }
        }
    }


}