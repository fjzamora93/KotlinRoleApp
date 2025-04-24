package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.roleapp.adventure.data.model.GameSession
import com.unir.roleapp.adventure.domain.GameSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameSessionViewModel @Inject constructor(
    private val repository: GameSessionRepository
) : ViewModel() {

    private val _gameSessions = MutableStateFlow<List<GameSession>>(emptyList())
    val gameSessions: StateFlow<List<GameSession>> = _gameSessions

    private val _selectedSession = MutableStateFlow<GameSession?>(null)
    val selectedSession: StateFlow<GameSession?> = _selectedSession

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchGameSessions()
    }


    fun fetchGameSessionById(sessionId: String) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            val result = repository.getGameSessionById(sessionId)
            _loading.value = false

            result.onSuccess { session ->
                _selectedSession.value = session
            }.onFailure { error ->
                _errorMessage.value = error.localizedMessage ?: "Error al buscar la sesión"
            }
        }
    }

    // Busca las sesiones por el ID del usuario actual (la id del usuario actual la obtenemos de la sesión, no te preocupes por eso ahora)
    fun fetchGameSessions() {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            val result = repository.getGameSessions()
            _loading.value = false

            result.onSuccess { sessions ->
                _gameSessions.value = sessions
            }.onFailure { error ->
                _errorMessage.value = error.localizedMessage ?: "Error al obtener sesiones"
            }
        }
    }

    fun addGameSession(session: GameSession) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            val result = repository.addGameSession(session)
            _loading.value = false

            result.onSuccess {
                fetchGameSessions() // Actualiza la lista tras agregar
            }.onFailure { error ->
                _errorMessage.value = error.localizedMessage ?: "Error al agregar sesión"
            }
        }
    }

    fun updateGameSession(sessionId: String, session: GameSession) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            val result = repository.updateGameSession(sessionId, session)
            _loading.value = false

            result.onSuccess {
                fetchGameSessions() // Refresca la lista si se actualiza bien
            }.onFailure { error ->
                _errorMessage.value = error.localizedMessage ?: "Error al actualizar sesión"
            }
        }
    }

    fun deleteGameSession(sessionId: String) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            val result = repository.deleteGameSession(sessionId)
            _loading.value = false

            result.onSuccess {
                fetchGameSessions() // Refresca la lista si se elimina bien
            }.onFailure { error ->
                _errorMessage.value = error.localizedMessage ?: "Error al eliminar sesión"
            }
        }
    }
}
