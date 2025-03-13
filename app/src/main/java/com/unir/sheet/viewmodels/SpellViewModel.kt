package com.unir.sheet.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.Spell
import com.unir.sheet.domain.usecase.spell.SpellUseCases
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
            println("La clase del personaje es:  "+ currentCharacter.rolClass.toString())
            result.onSuccess {
                    spells -> _spellList.value = spells
                println("VIendo hechizos disponibles para el personaje: $spells")

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