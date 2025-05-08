package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.roleapp.adventure.domain.model.CharacterContext
import com.unir.roleapp.adventure.domain.model.Adventure
import com.unir.roleapp.adventure.domain.usecase.DeleteAdventureUseCase
import com.unir.roleapp.adventure.domain.usecase.GetAdventureUseCase
import com.unir.roleapp.adventure.domain.usecase.SaveAdventureContextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdventureContextViewModel @Inject constructor(
    private val getAdventureUseCase: GetAdventureUseCase,
    private val deleteAdventureUseCase: DeleteAdventureUseCase,
    private val saveAdventureContextUseCase: SaveAdventureContextUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Se extrae el parámetro “adventureId” pasado por NavGraph
    private val adventureId: String = checkNotNull(savedStateHandle["adventureId"]) {
        "Navigation parameter 'adventureId' is missing"
    }

    // ————— Estados públicos expuestos al UI —————
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _historicalContext = MutableStateFlow("")
    val historicalContext: StateFlow<String> = _historicalContext.asStateFlow()

    private val _charContexts = MutableStateFlow<Map<Long, String>>(emptyMap())
    val charContexts: StateFlow<Map<Long, String>> = _charContexts.asStateFlow()

    private val _selectedCharacterIds = MutableStateFlow<List<Long>>(emptyList())
    val selectedCharacterIds: StateFlow<List<Long>> = _selectedCharacterIds.asStateFlow()

    init {
        loadAdventure()
    }

    /** Carga inicial de la aventura, sus contextos y personajes asociados */
    private fun loadAdventure() {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            _error.value = null

            getAdventureUseCase(adventureId)
                .onSuccess { adventure: Adventure ->
                    // Contexto histórico (puede ser null al principio)
                    _historicalContext.value = adventure.historicalContext.orEmpty()

                    // Mapear los CharacterContext que venían de la aventura
                    val initialMap = adventure.characterContexts
                        ?.associate { cc -> cc.characterId.toLong() to cc.context }
                        .orEmpty()

                    _charContexts.value = initialMap
                    _selectedCharacterIds.value = initialMap.keys.toList()
                }
                .onFailure { e ->
                    _error.value = e.message
                }

            _loading.value = false
        }
    }

    /** Añade o quita un personaje de la selección (hasta 4 máximo) */
    fun selectChar(charId: Long) {
        _selectedCharacterIds.value = _selectedCharacterIds.value.let { current ->
            if (charId in current) current - charId
            else if (current.size < 4) current + charId
            else current
        }
    }

    /** Actualiza sólo el texto del contexto histórico en memoria */
    fun updateHistoricalContext(newText: String) {
        _historicalContext.value = newText
    }

    /** Actualiza sólo el texto del contexto de un personaje concreto en memoria */
    fun updateCharContext(charId: Long, newContext: String) {
        _charContexts.value = _charContexts.value + (charId to newContext)
    }

    /** Borra la aventura completa y notifica al UI */
    fun deleteAdventure(onDone: () -> Unit = {}) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            _error.value = null

            deleteAdventureUseCase(adventureId)
                .onSuccess { onDone() }
                .onFailure { e -> _error.value = e.message }

            _loading.value = false
        }
    }

    /**
     * Guarda en Firestore:
     * 1) el contexto histórico
     * 2) todos los contextos de personajes seleccionados
     * y luego dispara el callback de éxito.
     */
    fun saveAll(onFinish: () -> Unit) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            _error.value = null

            // Construimos la lista de CharacterContext del dominio
            val toSave = _charContexts.value.map { (id, ctx) ->
                CharacterContext(id.toString(), ctx)
            }

            saveAdventureContextUseCase(adventureId, _historicalContext.value, toSave)
                .onSuccess { onFinish() }
                .onFailure { e -> _error.value = e.message }

            _loading.value = false
        }
    }
}
