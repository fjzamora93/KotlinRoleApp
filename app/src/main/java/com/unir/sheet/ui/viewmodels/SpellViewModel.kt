package com.unir.sheet.ui.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.Spell
import com.unir.sheet.data.repository.CharacterRepositoryImpl
import com.unir.sheet.data.repository.SpellRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SpellViewModel @Inject constructor(
    private val remoteSpellRepository: SpellRepository,
    private val characterRepository: CharacterRepositoryImpl
) : ViewModel(){

    private val _spellList = MutableLiveData<List<Spell>>()
    val spellList: LiveData<List<Spell>> get() = _spellList

    private val _spellDetail = MutableLiveData<Spell>()
    val spellDetail : LiveData<Spell> get() = _spellDetail


    // APlica un filtro de acuerdo al personaje
    fun getSpellsForCharacter(
        currentCharacter: CharacterEntity,
    ){
        viewModelScope.launch {
            val result = remoteSpellRepository.fetchSpellsByLevelAndRoleClass(currentCharacter.level, currentCharacter.rolClass.toString())
            println("La clase del personaje es:  "+ currentCharacter.rolClass.toString())
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