package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.roleapp.adventure.domain.model.AdventureSetup
import com.unir.roleapp.adventure.domain.model.AdventureAct
import com.unir.roleapp.adventure.domain.usecase.GenerateAdventureScriptUseCase
import com.unir.roleapp.adventure.domain.usecase.SaveAdventureUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdventureSetupViewModel @Inject constructor(
    private val generateUseCase: GenerateAdventureScriptUseCase,
    private val saveUseCase: SaveAdventureUseCase
) : ViewModel() {

    val title    = MutableStateFlow("")
    val description = MutableStateFlow("")
    val context  = MutableStateFlow("")
    val code     = MutableStateFlow("")
    val selectedCharacters = MutableStateFlow<List<String>>(emptyList())

    private val _acts = MutableStateFlow<List<AdventureAct>>(emptyList())
    val acts: StateFlow<List<AdventureAct>> = _acts

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error   = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun generateScript() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val setup = AdventureSetup(
                title.value,
                description.value,
                context.value,
                code.value,
                selectedCharacters.value
            )
            generateUseCase(setup)
                .onSuccess { _acts.value = it }
                .onFailure { _error.value = it.message }
            _loading.value = false
        }
    }

    fun saveAdventure(adventureId: String) {
        viewModelScope.launch {
            _loading.value = true
            saveUseCase(adventureId, _acts.value)
                .onFailure { _error.value = it.message }
            _loading.value = false
        }
    }
}