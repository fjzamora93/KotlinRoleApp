package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.roleapp.adventure.domain.model.Adventure
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureRequest
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAdventureViewModel @Inject constructor(
    private val createAdventureUseCase: CreateAdventureUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {


    // 1) Título y descripción que mete el máster
    private val _title       = MutableStateFlow("")
    val title: StateFlow<String>       = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    fun onTitleChange(v: String)       { _title.value = v }
    fun onDescriptionChange(v: String) { _description.value = v }


    // 2) Resultado de la creación
    private val _createdAdventure = MutableStateFlow<Adventure?>(null)
    val createdAdventure: StateFlow<Adventure?> = _createdAdventure.asStateFlow()

    // 3) UI state
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /** Lanza la creación en Firestore y notifica por callback. */
    fun createAdventure(onSuccess: (Adventure) -> Unit) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            _error.value   = null

            val req = CreateAdventureRequest(
                title.value,
                description.value
            )
            createAdventureUseCase(req)
                .onSuccess { adv ->
                    _createdAdventure.value = adv
                    onSuccess(adv)
                }
                .onFailure {
                    _error.value = it.message
                }

            _loading.value = false
        }
    }
}