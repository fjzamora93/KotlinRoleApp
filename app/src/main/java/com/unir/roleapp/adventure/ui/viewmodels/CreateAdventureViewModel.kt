package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roleapp.auth.domain.usecase.user.GetUserUseCase
import com.unir.roleapp.adventure.domain.model.Adventure
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureRequest
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateAdventureViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val createAdventureUseCase: CreateAdventureUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _title   = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description   = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _createdAdventure = MutableStateFlow<Adventure?>(null)
    val createdAdventure: StateFlow<Adventure?> = _createdAdventure.asStateFlow()

    fun onTitleChange(new: String)       { _title.value = new }
    fun onDescriptionChange(new: String) { _description.value = new }

    /**
     * Crea la aventura: obtiene primero el userId, monta el request completo
     * y lanza el caso de uso. Devuelve el nuevo Adventure vía callback.
     */
    fun createAdventure(onSuccess: (Adventure) -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value   = null

            // 1️⃣ Recuperar el userId
            val userResult = getUserUseCase()
            val uid = userResult.getOrNull()?.id
                ?: run {
                    _error.value = "Usuario no encontrado"
                    _loading.value = false
                    return@launch
                }

            // 2️⃣ Construir el request con todos los datos
            val req = CreateAdventureRequest(
                title       = _title.value,
                description = _description.value,
                userId      = uid.toString()
            )

            // 3️⃣ Llamar al caso de uso en IO
            val result = withContext(dispatcher) {
                createAdventureUseCase(req)
            }
            _loading.value = false

            // 4️⃣ Notificar resultado
            result
                .onSuccess { adv ->
                    _createdAdventure.value = adv
                    onSuccess(adv)
                }
                .onFailure {
                    _error.value = it.message
                }
        }
    }
}
