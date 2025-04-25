package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.roleapp.adventure.domain.model.CharacterContext
import com.unir.roleapp.adventure.domain.model.Character
import com.unir.roleapp.adventure.domain.usecase.DeleteAdventureUseCase
import com.unir.roleapp.adventure.domain.usecase.GetAdventureUseCase
import com.unir.roleapp.adventure.domain.usecase.SaveAdventureContextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class AdventureContextViewModel @Inject constructor(
    private val getAdventure: GetAdventureUseCase,
    private val saveContext: SaveAdventureContextUseCase,
    private val deleteAdventureUseCase: DeleteAdventureUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    // — título y descripción traídos de Firestore —
    private val _title       = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    // — contexto histórico que introduce el máster —
    private val _historicalContext = MutableStateFlow("")
    val historicalContext: StateFlow<String> = _historicalContext.asStateFlow()
    fun onHistoricalContextChange(v: String) {
        _historicalContext.value = v
    }

    // — IDs de personajes seleccionados (máx. 4) —
    private val _selectedCharacterIds = MutableStateFlow<List<Long>>(emptyList())
    val selectedCharacterIds: StateFlow<List<Long>> = _selectedCharacterIds.asStateFlow()
    fun selectChar(charId: Long) {
        _selectedCharacterIds.value = _selectedCharacterIds.value.let { current ->
            if (current.contains(charId)) current - charId
            else if (current.size < 4) current + charId
            else current
        }
    }

    // — texto de contexto por personaje —
    private val _charContexts = MutableStateFlow<Map<Long, String>>(emptyMap())
    val charContexts: StateFlow<Map<Long, String>> = _charContexts.asStateFlow()
    fun updateCharContext(charId: Long, ctx: String) {
        _charContexts.value = _charContexts.value.toMutableMap().also {
            it[charId] = ctx
        }
    }

    // — lista de personajes de prueba —
    // sustituye esto por tu lista real cuando la tengas
    val sampleChars = listOf(
        Character(id = 1L, name = "Personaje 1"),
        Character(id = 2L, name = "Personaje 2"),
        Character(id = 3L, name = "Personaje 3"),
        Character(id = 4L, name = "Personaje 4"),
    )

    // — control de UI —
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /** Carga título + descripción desde Firestore */
    fun loadAdventure(adventureId: String) = viewModelScope.launch(dispatcher) {
        _loading.value = true
        getAdventure(adventureId)
            .onSuccess { adv ->
                _title.value       = adv.title
                _description.value = adv.description
            }
            .onFailure {
                _error.value = it.message
            }
        _loading.value = false
    }

    /** Borra el documento completo de Firestore */
    fun deleteAdventure(adventureId: String, onDone: () -> Unit = {}) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            deleteAdventureUseCase(adventureId)
                .onFailure { _error.value = it.message }
            _loading.value = false
            onDone()
        }
    }

    /** Guarda contexto histórico + contextos de personajes en Firestore */
    fun saveAll(adventureId: String, onDone: () -> Unit) = viewModelScope.launch(dispatcher) {
        _loading.value = true
        _error.value   = null

        saveContext(
            adventureId,
            _historicalContext.value,
            _charContexts.value.map { (charId, ctx) -> CharacterContext(charId.toString(), ctx) }
        ).onFailure {
            _error.value   = it.message
            _loading.value = false
            return@launch
        }

        _loading.value = false
        onDone()
    }
}
