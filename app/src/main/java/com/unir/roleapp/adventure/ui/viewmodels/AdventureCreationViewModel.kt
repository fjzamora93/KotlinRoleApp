package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.roleapp.adventure.domain.model.Adventure
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureRequest
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AdventureCreationViewModel @Inject constructor(
    private val createAdventureUseCase: CreateAdventureUseCase
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _genre = MutableStateFlow("Fantasia")
    val genre: StateFlow<String> = _genre

    private val _creating = MutableStateFlow(false)
    val creating: StateFlow<Boolean> = _creating

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun onTitleChange(value: String) = _title.tryEmit(value)
    fun onDescriptionChange(value: String) = _description.tryEmit(value)
    fun onGenreChange(value: String) = _genre.tryEmit(value)

    fun createAdventure(onSuccess: (Adventure) -> Unit) {
        viewModelScope.launch {
            _creating.value = true
            _error.value   = null

            val request = CreateAdventureRequest(title.toString(), description.toString())
            createAdventureUseCase(request)
                .onSuccess { adv -> onSuccess(adv) }
                .onFailure { _error.value = it.message }

            _creating.value = false
        }
    }

}