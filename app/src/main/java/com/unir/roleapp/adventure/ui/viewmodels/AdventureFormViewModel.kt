package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.roleapp.auth.domain.usecase.user.GetUserUseCase
import com.unir.roleapp.adventure.domain.model.AdventureAct
import com.unir.roleapp.adventure.domain.model.Adventure
import com.unir.roleapp.adventure.domain.model.AdventureCharacter
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureRequest
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class AdventureFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserUseCase: GetUserUseCase,
    private val createAdventureUseCase: CreateAdventureUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

): ViewModel() {

    private val initialArg = savedStateHandle
        .get<String>("adventureId")
        .orEmpty()
    private val _isEditMode = MutableStateFlow(initialArg.isNotBlank())
    val isEditMode: StateFlow<Boolean> = _isEditMode.asStateFlow()

    init {
        if (initialArg.isNotBlank()) {
            loadAdventure(initialArg)
        }
    }

    private val _id   = MutableStateFlow("")
    val id: StateFlow<String> = _id.asStateFlow()

    private val _userId   = MutableStateFlow("")
    val userId: StateFlow<String> = _userId.asStateFlow()

    private val _title   = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description   = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _historicalContext   = MutableStateFlow("")
    val historicalContext: StateFlow<String> = _historicalContext.asStateFlow()

    private val _characters   = MutableStateFlow<List<AdventureCharacter>>(value = emptyList())
    val characters: StateFlow<List<AdventureCharacter>> = _characters.asStateFlow()

    private val _acts = MutableStateFlow(
        listOf(
            AdventureAct(
                actNumber = 1,
                title = "",
                narrative = "",
                mapDescription = "",
                mapURL = "",
                emptyList()
            )
        )
    )
    val acts: StateFlow<List<AdventureAct>> = _acts.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _createdAdventure = MutableStateFlow<Adventure?>(null)


    fun onChangeId(newId: String) {
        _id.value = newId
    }

    fun onChangeUserId(newUserId: String) {
        _userId.value = newUserId
    }

    fun onChangeTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun onChangeDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun onChangeHistoricalContext(newContext: String) {
        _historicalContext.value = newContext
    }

    fun onChangeCharacter(char: AdventureCharacter) {
        if (_characters.value.none { it.id == char.id }) {
            _characters.value = _characters.value + char
        }
    }

    /**
     * Añade un acto al final (automáticamente asigna el número de acto incremental).
     */
    fun addAct(act: AdventureAct) {
        val nextNumber = _acts.value.size + 1
        _acts.value += act.copy(actNumber = nextNumber)
    }

    /**
     * Actualiza un acto existente comparando por actNumber.
     */
    fun onChangeAct(updatedAct: AdventureAct) {
        _acts.value = _acts.value.map { if (it.actNumber == updatedAct.actNumber) updatedAct else it }
    }

    // Funciones de ayuda
    fun addCharacter(char: AdventureCharacter) {
        if (_characters.value.none() { it.id == char.id }) {
            _characters.value += char
        }
    }

    /**
     * Crea la aventura: obtiene primero el userId, monta el request completo
     * y lanza el caso de uso. Devuelve el nuevo Adventure vía callback.
     */
    fun createAdventure(onSuccess: (Adventure) -> Unit) {
        viewModelScope.launch {

            // Recuperar el userId
            val userResult = getUserUseCase()
            val uid = userResult.getOrNull()?.id
                ?: run {
                    _error.value = "Usuario no encontrado"
                    return@launch
                }
            onChangeUserId(uid.toString())

            // Construir el request con todos los datos
            val req = CreateAdventureRequest(
                title       = title.value,
                description = description.value,
                userId      = userId.value
            )

            // Llamar al caso de uso en IO
            val result = withContext(dispatcher) {
                createAdventureUseCase(req)
            }

            // Notificar resultado
            result
                .onSuccess { adv ->
                    _createdAdventure.value = adv
                    _id.value = adv.id
                    onSuccess(adv)
                }
                .onFailure {
                    _error.value = it.message
                }
        }
    }


    fun saveWholeAdventure(onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val adventureId = id.value
        if (adventureId.isBlank()) {
            onError(IllegalStateException("Falta el ID de la aventura"))
            return
        }
        // update con lo que hay en memoria
        val adventure = Adventure(
            id                = adventureId,
            userId            = userId.value,
            title             = title.value,
            description       = description.value,
            historicalContext = historicalContext.value,
            acts              = acts.value
        )
        // Envio de la Aventura a FireStore:
        viewModelScope.launch {
            try {
                db.collection("adventures")
                    .document(adventureId)
                    .set(adventure)
                    .await()
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun loadAdventure(adventureId: String, onError: (Exception) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("adventures")
                    .document(adventureId)
                    .get()
                    .await()
                val adventure = snapshot.toObject(Adventure::class.java)
                    ?: throw IllegalStateException("Aventura no encontrada")

                // Rellenamos los StateFlows
                _id.value                = adventure.id
                _userId.value            = adventure.userId
                _title.value             = adventure.title
                _description.value       = adventure.description
                _historicalContext.value = adventure.historicalContext
                _characters.value        = adventure.characters
                _acts.value              = adventure.acts
                // Si tuvieras también personajes en Firestore, cárgalos aquí

            } catch (e: Exception) {
                _error.value = e.message
                onError(e)
            }
        }
    }

    suspend fun sendPostToN8n(adventureId: String) {
        withContext(Dispatchers.IO) {
            val url = URL("https://d733-2a0c-5a84-730d-b00-5f1-42cc-c20a-de58.ngrok-free.app/webhook/generateAdventure")
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")

            val jsonInput = """{ "adventureId": "$adventureId" }"""

            val outputStreamWriter = OutputStreamWriter(connection.outputStream)
            outputStreamWriter.write(jsonInput)
            outputStreamWriter.flush()
            outputStreamWriter.close()

            val responseCode = connection.responseCode
            println("Response Code: $responseCode")

            connection.disconnect()
        }
    }

}