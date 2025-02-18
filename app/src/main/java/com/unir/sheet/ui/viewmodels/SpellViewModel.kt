package com.unir.sheet.ui.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.local.model.Item
import com.unir.sheet.data.local.model.RolCharacter
import com.unir.sheet.data.local.model.Spell
import com.unir.sheet.data.local.repository.LocalCharacterRepository
import com.unir.sheet.data.local.repository.LocalSkillRepository
import com.unir.sheet.data.remote.repository.RemoteSpellRepository
import com.unir.sheet.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SpellViewModel @Inject constructor(
    private val remoteSpellRepository: RemoteSpellRepository,
    private val localCharacterRepository: LocalCharacterRepository
) : ViewModel(){

    private val _spellList = MutableLiveData<List<Spell>>()
    val spellList: LiveData<List<Spell>> get() = _spellList

    private val _spellDetail = MutableLiveData<Spell>()
    val spellDetail : LiveData<Spell> get() = _spellDetail


    // APlica un filtro de acuerdo al personaje
    fun getSpellsForCharacter(
        currentCharacter: RolCharacter,
    ){
        viewModelScope.launch {
            val result = remoteSpellRepository.fetchSpellsByLevel(currentCharacter.level)
            result.onSuccess {
                    spells -> _spellList.value = spells
                println("VIendo hechizos disponibles para el personaje: $spells")

            }.onFailure {
                println("ALgo salió mal")
            }
        }
    }

    // Lanza una petición a la API para obtener los detalles del hechizo en cuestión
    fun getSpellDetail(
        spell: Spell
    ){
        viewModelScope.launch {
            val result = remoteSpellRepository.fetchSpells(filter = "")
        }
    }



}