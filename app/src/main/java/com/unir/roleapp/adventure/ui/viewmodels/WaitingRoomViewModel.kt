package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.roleapp.adventure.domain.model.Character
import com.unir.roleapp.adventure.domain.usecase.GetAdventureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de sala de espera.
 * Carga y expone la lista de personajes que se van uniendo a la aventura.
 */
@HiltViewModel
class WaitingRoomViewModel @Inject constructor(
    private val getAdventureUseCase: GetAdventureUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    // Estado de la lista de personajes actuales
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters.asStateFlow()

    // Estado de carga
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    // Estado de error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /**
     * Dispara la carga de personajes de la aventura desde Firestore.
     * Debe invocarse en cuanto se conozca el adventureId (por ejemplo, en LaunchedEffect).
     */
    fun loadCharacters(adventureId: String) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            _error.value = null

            // Petición al caso de uso
            getAdventureUseCase(adventureId)
                .onSuccess { adventure ->
                    /*_characters.value = adventure.characters*/
                }
                .onFailure { e ->
                    _error.value = e.message
                }

            _loading.value = false
        }
    }


}
